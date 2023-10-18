package com.lzh.forum.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LoginVo {
    /**
     * 登录成功后 返回给用户可显示的数据
     */
    private String name;

    private Integer age;

    private Integer gender;

    private String email;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String token;
}
