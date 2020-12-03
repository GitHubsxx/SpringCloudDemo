package com.scc.service;

import com.scc.domain.Video;

import java.util.List;

public interface IVideoService {
    //根据id查询视频
    public Video findById(Integer id);
}
