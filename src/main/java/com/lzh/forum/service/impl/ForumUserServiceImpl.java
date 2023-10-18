package com.lzh.forum.service.impl;

import com.lzh.forum.dto.AddUserDTO;
import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.mapper.ForumUserMapper;
import com.lzh.forum.service.ForumUserService;
import com.lzh.forum.vo.LoginVo;
import com.lzh.forum.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ForumUserServiceImpl implements ForumUserService {

    @Autowired
    ForumUserMapper forumUserMapper;

    /**
     * 验证token中的信息
     * @param name
     * @return
     */
    @Override
    public ForumUser validate(String name) {
        return forumUserMapper.validate(name);
    }

    @Override
    public PageVo getList(Integer page, Integer pageSize, String name) {

        Long totals = forumUserMapper.count(name);
        List<ForumUser> list = forumUserMapper.getList((page - 1) * pageSize, pageSize, name);

        return new PageVo(totals, list);
    }

    @Override
    public Long count(String name) {
        return forumUserMapper.count(name);
    }

    @Override
    public void register(AddUserDTO addUserDTO) {

        ForumUser forumUser = new ForumUser();
        BeanUtils.copyProperties(addUserDTO, forumUser);

        forumUser.setCreateTime(new Date());
        forumUser.setUpdateTime(new Date());
        forumUserMapper.register(forumUser);
    }

    @Override
    public void update(ForumUser forumUser) {
        forumUser.setUpdateTime(new Date());
        forumUserMapper.updateById(forumUser);
    }

    @Override
    public void delete(List<Integer> ids) {
        forumUserMapper.delete(ids);
    }
}
