package com.lzh.forum.service;

import com.lzh.forum.dto.LoginDTO;
import com.lzh.forum.dto.RegisterDTO;
import com.lzh.forum.entity.ForumUser;

public interface LoginAndRegisterService {

    ForumUser login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);
}
