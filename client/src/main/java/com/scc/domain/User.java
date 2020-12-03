package com.scc.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String gender;
    private String telephone;
    private String role;
}
