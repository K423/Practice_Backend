package com.lzh.forum.mapper;

import com.lzh.forum.entity.ForumUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginAndRegisterMapper {

    /**
     * 用户登录
     * @param name
     * @param password
     * @return
     */
    @Select("select * from forum_user where name = #{name} and password = #{password}")
    ForumUser login(String name, String password);

    /**
     * 用户注册
     * @param forumUser
     */
    @Insert("insert into forum_user (name, password, age, gender, email, phone, create_time, update_time) " +
            "VALUES (#{name}, #{password}, #{age}, #{gender}, #{email}, #{phone}, #{createTime}, #{updateTime})")
    void register(ForumUser forumUser);

}
