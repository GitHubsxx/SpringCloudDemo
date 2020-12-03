package com.scc.fegin;

import com.scc.domain.Video;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("redis")
public interface RedisFegin {
    @GetMapping("/redis/findById/{id}")
    public Video findById(@PathVariable("id") Integer id);
}
