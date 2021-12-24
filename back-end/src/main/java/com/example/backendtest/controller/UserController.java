package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.SignEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.UserService;
import com.example.backendtest.util.VerifyEmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
    private final VerifyEmailUtil verifyEmailUtil;

    // 测试后端是否正常工作
    @ApiOperation("测试：返回简单字符串")
    @GetMapping("/testGet")
    public String simpleReply() {
        return "Request success";
    }


    @SaCheckLogin
    @ApiOperation("以ID获取完整用户信息")
    @GetMapping("get")
    public UserEntity getUserById(Integer id) {
        return userService.getUserById(id);
    }

    @SaCheckLogin
    @ApiOperation("获取所有教师/助教信息")
    @GetMapping("get/teacher")
    public List<UserEntity> getAllTeachers() {
        return userService.getAllTeachers();
    }

    @SaCheckLogin
    @ApiOperation("获取所有学生信息")
    @GetMapping("get/student")
    public List<UserEntity> getAllStudents() {
        return userService.getAllStudents();
    }

    @SaCheckLogin
    @ApiOperation("获取所有管理员信息")
    @GetMapping("get/administrator")
    public List<UserEntity> getAllAdministrators() {
        return userService.getAllAdministrators();
    }

    // TODO: 管理员应该/有必要获取自己/其他管理员的信息吗？
    @SaCheckLogin
    @ApiOperation("获取所有用户信息")
    @GetMapping("get/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * 下面这个接口是管理员在增加用户时调用的，需要鉴权(管理员登录后才能调用)
     * 和用户注册的接口是共用的UserService的add()方法。
     * 虽然感觉怪怪的，但是暂时找不到更好的解决办法。
     * TODO: 可以有把这两个接口区分开的办法吗？
     */
    @SaCheckLogin
    @ApiOperation("新增用户")
    @PostMapping("add")
    public JSONObject add(@RequestBody UserEntity user) {
        return userService.add(user);
    }

    /**
     * 下面这个接口是用户自行注册时调用的，无需鉴权
     */
    @ApiOperation("新增用户")
    @PostMapping("register")
    public JSONObject register(@RequestBody UserEntity user) {
        return userService.add(user);
    }

    @SaCheckLogin
    @ApiOperation("移除用户")
    @DeleteMapping("remove")
    public JSONObject remove(Integer id) {
        return userService.remove(id);
    }

//    @ApiOperation("用户登录")
//    @GetMapping("login")
//    public JSONObject login(Integer id, String password) {
//        return userService.login(id, password);
//    }

    @ApiOperation("用户登录")
    @GetMapping("login")
    public SaResult login(Integer id, String password) {
        return userService.login(id, password);
    }

    @SaCheckLogin
    @ApiOperation("用户注销（退出登录）")
    @GetMapping("logout")
    public SaResult logout(Integer id) {
        return userService.logout(id);
    }

    @ApiOperation("获取当前登录状态")
    @GetMapping("isLogin")
    public int isLogin() {
        return userService.isLogin();
    }

    @SaCheckLogin
    @ApiOperation("查询某ID用户是否是当前登录用户")
    @GetMapping("isLogin/id")
    public int isLoginById(Integer id) {
        return userService.isLoginById(id);
    }

    @SaCheckLogin
    @ApiOperation("更新用户信息")
    @PutMapping("update")
    public JSONObject updateUserInfo(@RequestBody UserEntity user) {
        return userService.updateUserInfo(user);
    }

    @SaCheckLogin
    @ApiOperation("管理员更新用户信息")
    @PutMapping("update/administrator")
    public JSONObject administratorUpdateUserInfo(@RequestBody UserEntity user) {
        return userService.administratorUpdateUserInfo(user);
    }

    @SaCheckLogin
    @PostMapping ("/verify/send")
    @ApiOperation("发送验证码到邮箱")
    public JSONObject sendVerifyCode(String email, Integer userId) {
        return verifyEmailUtil.sendVerificationEmail(email, userId);
    }

//    @PostMapping("/verify")
//    @ApiOperation("检验验证码")
//    public JSONObject verify(String inputCode, HttpServletRequest request){
//       return verifyEmailUtil.checkCode(inputCode,request);
//    }

    @SaCheckLogin
    @ApiOperation("检验验证码并激活账号")
    @PostMapping("/verify/activate")
    public JSONObject verifyCodeAndActivateAccount(String code, Integer userId) {
        return verifyEmailUtil.checkVerificationCode(code, userId);
    }

//    @PostMapping("/verifyAndActivate")
//    @ApiOperation("检验验证码并激活")
//    public JSONObject verify(String inputCode, HttpServletRequest request,@RequestBody UserEntity user){
//
//        JSONObject jsonObject = new JSONObject();
//        JSONObject json = new JSONObject();
//        jsonObject = verifyEmailUtil.checkCode(inputCode,request);
//        if(jsonObject.getInteger("status")==200)
//        {
//            //进行激活
//            userService.administratorUpdateUserInfo(user);
//            json.put("status", 200);
//            json.put("message", "验证成功并激活");
//        }
//        else
//        {
//            json.put("status", 500);
//            json.put("message", "验证错误");
//        }
//        return json;
//    }

    @PutMapping("/sign")
    @ApiOperation("学生签到")
    public JSONObject sign(int userId)
    {
        return userService.sign(userId);
    }

    @GetMapping("/getSign")
    @ApiOperation("获得学生的签到信息")
    public SignEntity getSign(int userId)
    {
        return userService.getSign(userId);
    }
}
