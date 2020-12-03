package com.scc.controller;

import com.scc.domain.Video;
import com.scc.fegin.RedisFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/redis")
@Controller
public class RedisController {
    @Autowired
    private RedisFegin redisFegin;

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Video video=redisFegin.findById(id);
        model.addAttribute("video", video);
        return "video_update";
    }
}
