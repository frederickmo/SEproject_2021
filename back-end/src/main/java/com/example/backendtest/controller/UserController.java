package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // 测试后端是否正常工作
    @ApiOperation("测试：返回简单字符串")
    @GetMapping("/testGet")
    public String simpleReply() {
        return "Request success";
    }


    // 以下注解用于登录后鉴权
    @SaCheckLogin
    @ApiOperation("以ID获取完整用户信息")
    @GetMapping("get")
    public UserEntity getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @ApiOperation("新增用户/用户注册")
    @PostMapping("add")
    public String register(@RequestBody UserEntity user) {
        return userService.addUser(user);
    }

    @ApiOperation("用户登录")
    @GetMapping("login")
    public JSONObject login(Integer id, String password) {
        return userService.login(id, password);
    }

    @ApiOperation("获取当前登录状态")
    @GetMapping("isLogin")
    public int isLogin() {
        return userService.isLogin();
    }

    @ApiOperation("更新用户信息")
    @PostMapping("update")
    public JSONObject updateUserInfo(@RequestBody UserEntity user) {
        return userService.updateUserInfo(user);
    }

}
