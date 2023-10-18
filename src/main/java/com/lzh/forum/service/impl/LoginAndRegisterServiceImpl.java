package com.lzh.forum.service.impl;

import com.lzh.forum.dto.LoginDTO;
import com.lzh.forum.dto.RegisterDTO;
import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.mapper.LoginAndRegisterMapper;
import com.lzh.forum.service.LoginAndRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {

    @Autowired
    LoginAndRegisterMapper loginAndRegisterMapper;

    @Override
    public ForumUser login(LoginDTO loginDTO) {
        String name = loginDTO.getName();
        String password = loginDTO.getPassword();
        return loginAndRegisterMapper.login(name, password);
    }

    @Override
    public void register(RegisterDTO registerDTO) {

        /**
         * 判断用户是否已存在---用户名作为唯一标示
         */

        ForumUser forumUser = new ForumUser();
        BeanUtils.copyProperties(registerDTO, forumUser);
        forumUser.setCreateTime(new Date());
        forumUser.setUpdateTime(new Date());
        loginAndRegisterMapper.register(forumUser);
    }
}
