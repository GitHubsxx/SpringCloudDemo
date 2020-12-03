package com.scc.controller;

import com.scc.domain.Video;
import com.scc.domain.VideoVo;
import com.scc.fegin.VideoFegin;
import com.scc.log.annotation.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoFegin videoFegin;

    @GetMapping("/findAll")
    @ResponseBody
    public VideoVo findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
        VideoVo videoVo=videoFegin.finAll(page,limit);
        return  videoVo;
    }

    @GetMapping("/delete/{id}")
    public String  delete(@PathVariable("id") Integer id){
        videoFegin.delete(id);
        return "video_manage";
    }

    @PostMapping("/save")
    public String save(Video video){
        videoFegin.save(video);
        return "video_manage";
    }

    @PostMapping("/update")
    public String update(Video video){
        videoFegin.update(video);
        return "video_manage";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id,Model model){
        Video video=videoFegin.findById(id);
        model.addAttribute("video", video);
        return "video_update";
    }

    @GetMapping("/findDetails/{id}")
    public String findDetails(@PathVariable("id") Integer id,Model model){
        Video video=videoFegin.findById(id);
        model.addAttribute("video", video);
        return "video_details";
    }
    @LogRecord(system="111",module = "222")
    @GetMapping("/findByCategory/{category}")
    public String findByCategory(@PathVariable("category") String category,Model model){
        List<Video> videos=videoFegin.finByCategory(category);
        model.addAttribute("videos", videos);
        return "video_list";
    }

    @GetMapping("/findByName")
    public String findByName(@RequestParam("name") String name,Model model){
        List<Video> videos=videoFegin.finByName(name);
        model.addAttribute("videos", videos);
        return "video_list";
    }


}
