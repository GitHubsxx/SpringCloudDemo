package com.scc.fegin;

import com.scc.domain.Video;
import com.scc.domain.VideoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("video")
public interface VideoFegin {

    @GetMapping("/video/findAll/{page}/{limit}")
    public VideoVo finAll(@PathVariable("page") int page, @PathVariable("limit") int limit);

    @GetMapping("/video/findById/{id}")
    public Video findById(@PathVariable("id") Integer id);

    @GetMapping("/video/findByCategory/{category}")
    public List<Video> finByCategory(@PathVariable("category") String category);

    @GetMapping("/video/findByName")
    public List<Video> finByName(@RequestParam("name") String name);

    @DeleteMapping("/video/delete/{id}")
    public void delete(@PathVariable("id") Integer id);

    @PostMapping("/video/save")
    public void save(@RequestBody Video video);

    @PutMapping("/video/update")
    public void update(@RequestBody Video video);

}
