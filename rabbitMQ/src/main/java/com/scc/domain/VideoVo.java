package com.scc.domain;

import lombok.Data;

import java.util.List;

@Data
public class VideoVo {
    private int code;
    private String msg;
    private int count;
    private List<Video> data;
}
