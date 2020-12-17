package com.scc.redpac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scc.redpac.domain.RedPacketRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedPacketRecordDao extends BaseMapper<RedPacketRecord> {
}
