package com.scc.redpac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scc.redpac.dao.RedPacketInfoDao;
import com.scc.redpac.dao.RedPacketRecordDao;
import com.scc.redpac.domain.RedPacketInfo;
import com.scc.redpac.domain.RedPacketRecord;
import com.scc.redpac.service.IRedPacketService;
import com.scc.redpac.utils.SnowflakeIdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;


@Service
public class RedPacketService extends ServiceImpl<RedPacketInfoDao, RedPacketInfo> implements IRedPacketService {
    private static final String TOTAL_NUM = "_totalNum";
    private static final String TOTAL_AMOUNT = "_totalAmount";
    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    @Autowired
    private RedPacketInfoDao redPacketInfoDao;
    @Autowired
    private RedPacketRecordDao redPacketRecordDao;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 发红包
     * @param redPacketInfo
     * @return
     */
    @Override
    public String saveRedPacket(RedPacketInfo redPacketInfo) {
        // 组装数据
        // 雪花算法生成唯一id
        long redPacketId = idWorker.nextId();
        RedPacketInfo record = new RedPacketInfo();
        record.setId(redPacketId+1);
        record.setUid(redPacketInfo.getUid());
        record.setTotalAmount(redPacketInfo.getTotalAmount());
        record.setTotalPacket(redPacketInfo.getTotalPacket());
        record.setCreateTime(new Date());
        record.setRemainingAmount(redPacketInfo.getTotalAmount());
        record.setRemainingPacket(redPacketInfo.getTotalPacket());
        record.setRedPacketId(redPacketId);
        // 红包保存到数据库
        super.save(record);
        // 红包个数和总金额存入缓存
        stringRedisTemplate.opsForValue().set(redPacketId + "_totalNum", redPacketInfo.getTotalPacket() + "");
        stringRedisTemplate.opsForValue().set(redPacketId + "_totalAmount", redPacketInfo.getTotalAmount() + "");
        return "success";
    }
    /**
     * 抢红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @Override
    public String getRedPacket(Long redPacketId, Integer uid) {
        Object record = stringRedisTemplate.opsForValue().get(uid + "" + redPacketId);
        // 如果用户已经抢过红包了,那点击抢红包就应该是查看抢红包的详细记录
        if (StringUtils.isNotBlank((String)record)){
            System.out.println("红包详细记录: "+record+"分钱");
            return "红包详细记录";
        }
        // 查询红包剩余个数
        String redPacketName = redPacketId + TOTAL_NUM;
        String num = (String) stringRedisTemplate.opsForValue().get(redPacketName);
        if (StringUtils.isNotBlank(num)) {
            System.out.println("红包剩余个数: "+num);
            return num;
        }
        return "0";
    }
    /**
     * 拆红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @Override
    public String getRedPacketMoney(Integer uid, Long redPacketId) {
        //可以放到缓存map中
        // 抢到的红包金额
        Integer randomAmount = 0;
        String redPacketName = redPacketId + TOTAL_NUM;
        String totalAmountName = redPacketId + TOTAL_AMOUNT;
        // 预减获取红包剩余数量，decr原子减来防止领取人数超过红包个数
        long decr = -1;
        //decr 减一  decrby 减指定数量
        try {
            decr = stringRedisTemplate.getConnectionFactory().getConnection().decr(
                    redPacketName.getBytes("UTF-8"));//redis decreby功能
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (decr<0){
            System.out.println(uid+"：  抱歉！红包已经抢完了."+"decr="+decr);
            return "抱歉！红包已经抢完了";
        }
        // 下面就开始随机分配金额了，并发下可能领取人数的业务逻辑同时走到了这里，
        // 下面算法最后计算出来的金额就会和总金额有偏差，所以我们可以通过对红包
        // id进行路由，放入同一个队列里面，从而保证顺序消费，
        // 这样金额总和就和总金额不会有偏差

        // 剩余总金额(后面所有逻辑，都由下游业务去队列里面执行)
        synchronized (this){
            Integer totalAmountInt = Integer.parseInt((String)stringRedisTemplate.opsForValue().get(totalAmountName));
            if(0==decr)
                //最后一个
                randomAmount = totalAmountInt;
            else{
                // 剩余金额 / 剩余红包个数 * 2 = 最大红包金额
                Integer maxMoney = (int) (totalAmountInt / (decr + 1) * 2);
                Random random = new Random();
                // 红包取值随机数，不超过最大金额(如果是最后一个红包，金额就是剩下的所有钱)
                randomAmount = random.nextInt(maxMoney);
            }
            System.out.println(uid+"：  抢到了  "+randomAmount+"   分钱");
            // 红包剩余个数减1，同时剩余金额也要减少
            try {
                stringRedisTemplate.getConnectionFactory().getConnection().decrBy(
                        totalAmountName.getBytes("UTF-8"),randomAmount);//redis decreby功能
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 数据库插入抢红包记录
            updateRacketInDB(uid, redPacketId,randomAmount);
            //更新缓存
            stringRedisTemplate.opsForValue().set(uid + "" + redPacketId,randomAmount.toString());
        }
        return randomAmount + "";
    }
    public void updateRacketInDB(int uid, long redPacketId, int amount) {
        // 数据库插入抢红包记录
        // 雪花算法生成唯一id
        long onlyId = idWorker.nextId();
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setId(onlyId+2);
        redPacketRecord.setUid(uid);
        redPacketRecord.setRedPacketId(redPacketId);
        redPacketRecord.setAmount(amount);
        redPacketRecord.setCreateTime(new Date());
        this.redPacketRecordDao.insert(redPacketRecord);
        // 查询到红包信息
        LambdaQueryWrapper<RedPacketInfo> wrapper = new LambdaQueryWrapper();
        wrapper.eq(RedPacketInfo::getRedPacketId,redPacketId);
        RedPacketInfo redPacketInfo = this.redPacketInfoDao.selectOne(wrapper);
        // 修改红包剩余信息
        redPacketInfo.setRemainingPacket(redPacketInfo.getRemainingPacket()-1);
        redPacketInfo.setRemainingAmount(redPacketInfo.getRemainingAmount()-amount);
        redPacketInfo.setUpdateTime(new Date());
        this.redPacketInfoDao.updateById(redPacketInfo);
    }
}
