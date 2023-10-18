package com.lzh.forum.controller;

import com.lzh.forum.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试控制器")
public class TestController {

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public Result test(){
        return Result.success("越过拦截,继续访问......");
    }
}
