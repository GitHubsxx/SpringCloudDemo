package com.scc.controller;

import com.scc.domain.Video;
import com.scc.domain.VideoVo;
import com.scc.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/video")
@RestController
public class VideoController {
    @Autowired
    private IVideoService videoService;

    //查询所有视频
    @GetMapping("/findAll/{page}/{limit}")
    public VideoVo findAll(@PathVariable("page") int page, @PathVariable("limit") int limit) throws Exception {
        List<Video> videos=videoService.findAll(page,limit);
        VideoVo videoVo = new VideoVo();
        videoVo.setCode(0);
        videoVo.setMsg("");
        videoVo.setCount(videoService.count());
        videoVo.setData(videos);
        return videoVo;
    }

    //删除视频
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) throws Exception{
        videoService.delete(id);
    }

    //添加视频
    @PostMapping("/save")
    public void save(@RequestBody Video video) throws Exception{
        videoService.save(video);
    }

    //编辑视频
    @PutMapping("/update")
    public void update(@RequestBody Video video) throws Exception{
        videoService.update(video);
    }

    //根据id查询视频
    @GetMapping("/findById/{id}")
    public Video findById(@PathVariable("id") Integer id) throws Exception{
        return videoService.findById(id);
    }

    //查询所有视频
    @GetMapping("/findByCategory/{category}")
    public List<Video> findByCategory(@PathVariable("category") String category) throws Exception {
        List<Video> videos=videoService.findByCategory(category);
        return videos;
    }

    //根据名称模糊查询视频
    @GetMapping("/findByName")
    public List<Video> findByName(@RequestParam("name") String name) throws Exception {
        List<Video> videos=videoService.findByName(name);
        return videos;
    }
}
