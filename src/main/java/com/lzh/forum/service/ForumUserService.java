package com.lzh.forum.service;

import com.lzh.forum.dto.AddUserDTO;
import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.vo.PageVo;

import java.util.List;

public interface ForumUserService {
    /**
     * 验证token中的信息
     * @param name
     * @return
     */
    ForumUser validate(String name);

    /**
     * 获取数据列表
     * @return
     */
    PageVo getList(Integer page, Integer pageSize, String name);

    /**
     * 查询数据总量
     * @param name
     * @return
     */
    Long count(String name);

    void register(AddUserDTO addUserDTO);

    void update(ForumUser forumUser);

    void delete(List<Integer> ids);
}
