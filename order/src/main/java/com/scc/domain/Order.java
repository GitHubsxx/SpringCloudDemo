package com.scc.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private Date date;
    private User user;
    private Video video;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", video=" + video +
                '}';
    }
}
