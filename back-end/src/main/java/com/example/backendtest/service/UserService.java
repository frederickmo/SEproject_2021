package com.example.backendtest.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.repository.UserRepository;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.util.JwtUtil;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;

//    private static Map<Integer, UserEntity> userMap = new HashMap<>();

    public String addUser(UserEntity user) {
        Optional<UserEntity> userTemp = userRepository.findById(user.getId());
        if (userTemp.isPresent()) {
            throw new IllegalStateException("该用户已存在");
        }
        // TODO: 尝试写验证邮箱唯一的方法？
        // ↓已经写了
        else if (userRepository.findAllByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("该邮箱已被注册");
        } else {
            user.setActivated((byte)0);
            user.setGender(0);
            user.setIdentity(0);
            userRepository.save(user);
            return "注册成功";
        }
    }

    public int isLogin() {
        if (StpUtil.isLogin()) {
            return 1;
        } else {
            return 0;
        }
    }

    public JSONObject login(Integer id, String password) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("该用户不存在");
        } else {
            if (userOptional.get().getPassword().equals(password)) {
                // 登录，Sa-Token标记当前Session登录的用户id
                StpUtil.login(id);
//                return "登陆成功";

                log.info("登陆成功，生成token");
                String token = JwtUtil.createToken(id);
                JSONObject json = new JSONObject();
                try {
                    json.put("status", 200);
                    json.put("token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return json;

            } else {
                throw new IllegalStateException("密码不正确");
            }
        }
    }

    public UserEntity getUserById(Integer id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("用户不存在");
        } else {
            return userOptional.get();
        }
    }

    public JSONObject updateUserInfo(UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("用户" + user.getId() + "不存在");
        } else {
            userOptional.get().setGender(user.getGender());
            log.info("用户修改性别为 " + user.getGender());
            userOptional.get().setName(user.getName());
            log.info("用户修改姓名为 " + user.getName());
            userRepository.save(userOptional.get());
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "修改成功");
            return json;
        }
    }
}
