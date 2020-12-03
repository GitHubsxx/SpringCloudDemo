package com.scc.domain;

import lombok.Data;

@Data
public class Video {
    private Integer id;
    private String name;
    private String category;
    private String imgurl;
    private String description;
}
