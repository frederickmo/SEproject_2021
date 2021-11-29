package com.example.backendtest.controller;

import com.example.backendtest.repository.UserRepository;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    @Resource
    private UserRepository userRepository;

    private final UserService userService;

    // 测试后端是否正常工作
    @ApiOperation("测试：返回简单字符串")
    @GetMapping("/testGet")
    public String simpleReply() {
        return "Request success";
    }


    @ApiOperation("以ID获取完整用户信息")
    @GetMapping("get")
    public UserEntity getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @ApiOperation("新增用户/用户注册")
    @PostMapping("add")
    public void register(@RequestBody UserEntity user) {
        userService.addUser(user);
    }

    @ApiOperation("用户登录")
    @GetMapping("login")
    public String login(Integer id, String password) {
        return userService.login(id, password);
    }

}
