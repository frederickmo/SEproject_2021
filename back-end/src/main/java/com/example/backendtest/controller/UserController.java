package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.UserService;
import com.example.backendtest.util.VerifyEmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    VerifyEmailUtil verifyEmailUtil;

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

    @ApiOperation("获取所有教师/助教信息")
    @GetMapping("get/teacher")
    public List<UserEntity> getAllTeachers() {
        return userService.getAllTeachers();
    }

    @ApiOperation("获取所有学生信息")
    @GetMapping("get/student")
    public List<UserEntity> getAllStudents() {
        return userService.getAllStudents();
    }

    @ApiOperation("获取所有管理员信息")
    @GetMapping("get/administrator")
    public List<UserEntity> getAllAdministrators() {
        return userService.getAllAdministrators();
    }

    // TODO: 管理员应该/有必要获取自己/其他管理员的信息吗？
    @ApiOperation("获取所有用户信息")
    @GetMapping("get/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation("新增用户/用户注册")
    @PostMapping("add")
    public JSONObject add(@RequestBody UserEntity user) {
        return userService.add(user);
    }

    @ApiOperation("移除用户/用户注销")
    @DeleteMapping("remove")
    public JSONObject remove(Integer id) {
        return userService.remove(id);
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
    @PutMapping("update")
    public JSONObject updateUserInfo(@RequestBody UserEntity user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping ("/mail")
    @ApiOperation("发送验证码到邮箱")
    public JSONObject sendVerifyCode(HttpServletRequest request, String email) {
        return verifyEmailUtil.sendMail(request,email);
    }

    @GetMapping("/verify")
    @ApiOperation("检验验证码")
    public JSONObject verify(String inputCode, HttpServletRequest request){
        return verifyEmailUtil.checkCode(inputCode,request);
    }

}
