package com.lzh.forum.controller;

import com.lzh.forum.common.Result;
import com.lzh.forum.dto.AddUserDTO;
import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.service.ForumUserService;
import com.lzh.forum.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "数据控制器")
@Slf4j
@RestController
public class ForumUserController {

    @Autowired
    private ForumUserService forumUserService;

    @ApiOperation(value = "数据查询接口")
    @GetMapping("/infolist")
    public Result getList(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "5")Integer pageSize,
                          String name){

        log.info("数据查询...." + page + "-------" + pageSize);
        PageVo pageVo = forumUserService.getList(page, pageSize, name);
        log.info("查询数据为：" + pageVo.getRows());
        log.info("+++++++++++++++++++++++++++++++++++++++");
        return Result.success(pageVo);
    }

    @ApiOperation(value = "后台数据新增接口")
    @PostMapping("/adduser")
    public Result addUser(@RequestBody AddUserDTO addUserDTO){
        //用户名为唯一标识
        String name = addUserDTO.getName();
        String password = addUserDTO.getPassword();
        String email = addUserDTO.getEmail();
        if (name.equals("") || password.equals("") || email.equals("")){
            return Result.error("NotBlank");
        }
        ForumUser user = forumUserService.validate(name);
        if (user == null){
            forumUserService.register(addUserDTO);
            return Result.success("suc");
        }
        log.info("用户已存在....");
        return Result.error("error");
    }

    @ApiOperation(value = "数据修改接口")
    @PostMapping("/updateuser")
    public Result updateUser(@RequestBody ForumUser forumUser){

        log.info("数据修改....");
        log.info("修改数据为" + forumUser);
        forumUserService.update(forumUser);
        log.info("+++++++++++++++++++++++++++++++++++++++");
        return Result.success("suc");
    }

    @ApiOperation(value = "数据删除接口")
    @DeleteMapping("/deleteuser/{ids}")
    public Result deleteUser(@PathVariable List<Integer> ids){

        log.info("数据删除...");
        log.info("删除数据id为" + ids);
        forumUserService.delete(ids);
        log.info("+++++++++++++++++++++++++++++++++++++++");
        return Result.success("suc");
    }
}
