package com.scc.service;

import com.scc.domain.Video;

import java.util.List;

public interface IVideoService {
    //查询所有视频
    public List<Video> findAll(int page,int limit);

    //查询记录条数
    public int count();

    //根据id查询视频
    public Video findById(Integer id);

    //根据类型查询视频
    public List<Video> findByCategory(String category);

    //根据名称查询视频
    public List<Video> findByName(String name);

    //保存视频
    public void save(Video video);

    //修改视频
    public void update(Video video);

    //删除视频
    public void delete(Integer id);
}
