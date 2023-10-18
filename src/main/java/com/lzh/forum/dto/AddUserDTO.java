package com.lzh.forum.dto;

import lombok.Data;

@Data
public class AddUserDTO {
    private String name;
    private String password;
    private String email;
    private Integer age;
    private Integer gender;
    private String phone;
    private String avatar;
}
