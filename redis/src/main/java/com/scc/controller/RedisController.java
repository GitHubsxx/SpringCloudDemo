package com.scc.controller;

import com.scc.domain.Video;
import com.scc.domain.VideoVo;
import com.scc.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/redis")
@RestController
public class RedisController {
    @Autowired
    private IVideoService videoService;

    //根据id查询视频
    @GetMapping("/findById/{id}")
    public Video findById(@PathVariable("id") Integer id) throws Exception{
        return videoService.findById(id);
    }
}
