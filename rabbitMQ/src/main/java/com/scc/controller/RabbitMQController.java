package com.scc.controller;

import com.scc.domain.Video;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rabbitMq")
@RestController
public class RabbitMQController {
    @GetMapping("/findById/{id}")
    public Video findById(@PathVariable("id") Integer id) throws Exception{
        return null;
    }
}
