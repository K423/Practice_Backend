package com.lzh.forum.mapper;

import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.vo.LoginVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ForumUserMapper {
    /**
     * 验证token中的信息
     * @param name
     * @return
     */
    @Select("select * from forum_user where name = #{name}")
    ForumUser validate(String name);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    List<ForumUser> getList(Integer page, Integer pageSize, String name);

    /**
     * 查询数据总数
     * @param name
     * @return
     */
    Long count(String name);

    /**
     * 后台新增数据
     * @param forumUser
     */
    @Insert("insert into forum_user (name, password, age, gender, email, phone, create_time, update_time, avatar) " +
            "VALUES (#{name}, #{password}, #{age}, #{gender}, #{email}, #{phone}, #{createTime}, #{updateTime}, #{avatar})")
    void register(ForumUser forumUser);

    void updateById(ForumUser forumUser);

    void delete(List<Integer> ids);
}
