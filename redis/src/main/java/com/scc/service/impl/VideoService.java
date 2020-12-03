package com.scc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scc.dao.VideoDao;
import com.scc.domain.Video;
import com.scc.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class VideoService implements IVideoService {
    @Autowired
    private VideoDao videoDao;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Video findById(Integer id) {
        String body = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //key就是uri,如果redis有这个key，就从缓存中获取，没有的话，就去请求
        if(stringRedisTemplate.hasKey("test")){
            //redis缓存数据
            String value = stringRedisTemplate.opsForValue().get("test");//根据key获取缓存中的val
            body = ops.get(id);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Video data = objectMapper.readValue(body, Video.class);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //如果缓存中没有，需要取出数据，并存入缓存
            Video v =  videoDao.findById(id);
            //stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
            stringRedisTemplate.opsForValue().set("test","哈哈哈哈");
            ResponseEntity<String> weatherData = restTemplate.getForEntity("http://www.baidu.com", String.class);
            //使用ObjectMapper进行处理
            ObjectMapper mapper = new ObjectMapper();
            if(weatherData.getStatusCodeValue() == 200){
                body = weatherData.getBody();
                Video weatherData2 = null;
                try {
                    weatherData2 = mapper.readValue(body, Video.class);
                    ops.set(id.toString(), body, 5*60*1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return weatherData2;
            }
        }
        return null;
    }

    }
