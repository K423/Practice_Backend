package com.lzh.forum.vo;

import com.lzh.forum.entity.ForumUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 分页结果查询实体类
 * 查询数据总数量
 * 单页展示数目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    private Long totals;
    private List<ForumUser> rows;
    /**
     * 可以自定义List中的数据类型
     * 若要返回全部数据详情
     * 不设置类型或设置为ForumUser即可,
     * 再更改mapper文件查询类型
     */
}
