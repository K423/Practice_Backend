package com.lzh.forum.controller;


import com.lzh.forum.common.Result;
import com.lzh.forum.dto.LoginDTO;
import com.lzh.forum.dto.RegisterDTO;
import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.service.LoginAndRegisterService;
import com.lzh.forum.service.impl.ForumUserServiceImpl;
import com.lzh.forum.utils.JwtUtils;
import com.lzh.forum.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "注册登录控制器")
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginAndRegisterController {

    @Autowired
    LoginAndRegisterService loginAndRegisterService;

    @Autowired
    ForumUserServiceImpl forumUserService;

    /**
     *get 与 @RequestParam 才是一对
     *
     * post 与 @RequestBody 是一对
     *
     * 对于 get 请求，有时候想用实体类接受参数，就光秃秃放个实体类就行，
     * 而且当参数与属性名称不对应时也不会报错，只是不会对其赋值而已
     */


    @ApiOperation(value = "用户登录接口")
    @GetMapping("/login")
    public Result login(LoginDTO loginDTO){

        ForumUser user = loginAndRegisterService.login(loginDTO);
        if (user!=null){
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(user, loginVo);
            //登录成功 生成jwt
            String token = JwtUtils.generateToken(loginVo.getName());
            loginVo.setToken(token);
            log.info("用户登录信息" + loginVo);
            return Result.success(loginVo);
        }
        return Result.error("登录失败");
    }

    @ApiOperation(value = "用户注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        //用户名为唯一标识
        String name = registerDTO.getName();
        String password = registerDTO.getPassword();
        String email = registerDTO.getEmail();
        if (name.equals("") || password.equals("") || email.equals("")){
            return Result.error("不能为空....");
        }
        ForumUser user = forumUserService.validate(name);
        if (user == null){
            loginAndRegisterService.register(registerDTO);
            return Result.success("注册成功");
        }
        log.info("用户已存在....");
        return Result.error("注册失败");
    }
}

