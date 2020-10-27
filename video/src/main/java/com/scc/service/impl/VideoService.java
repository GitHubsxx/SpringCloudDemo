package com.scc.service.impl;

import com.scc.dao.VideoDao;
import com.scc.domain.Video;
import com.scc.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService implements IVideoService {
    @Autowired
    private VideoDao videoDao;

    @Override
    public List<Video> findAll(int page,int limit) {
        return videoDao.findAll(page,limit);
    }

    @Override
    public int count() {
        return videoDao.count();
    }

    @Override
    public Video findById(Integer id) {
        return videoDao.findById(id);
    }

    @Override
    public List<Video> findByCategory(String category) {
        return videoDao.findByCategory(category);
    }

    @Override
    public List<Video> findByName(String name) {
        return videoDao.findByName(name);
    }

    @Override
    public void save(Video video) {
        videoDao.save(video);
    }

    @Override
    public void update(Video video) {
        videoDao.update(video);
    }

    @Override
    public void delete(Integer id) {
        videoDao.delete(id);
    }
}
