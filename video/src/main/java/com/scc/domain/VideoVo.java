package com.scc.domain;

import lombok.Data;

import java.util.List;

@Data
public class VideoVo {
    private int code;
    private String msg;
    private int count;
    private List<Video> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Video> getData() {
        return data;
    }

    public void setData(List<Video> data) {
        this.data = data;
    }
}
