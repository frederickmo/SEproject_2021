package com.example.backendtest.service;

import com.example.backendtest.repository.UserRepository;
import com.example.backendtest.model.UserEntity;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void addUser(UserEntity user) {
        Optional<UserEntity> userTemp = userRepository.findById(user.getId());
        if (userTemp.isPresent()) {
            throw new IllegalStateException("该用户已存在");
        }
        // TODO: 尝试写验证邮箱唯一的方法？
        else if (userRepository.findAllByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("该邮箱已被注册");
        } else {
            user.setActivated((byte)0);
            user.setGender(0);
            user.setIdentity(0);
            userRepository.save(user);
        }
    }

    public String login(Integer id, String password) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("该用户不存在");
        } else {
            if (userOptional.get().getPassword().equals(password)) {
                return "登陆成功";
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
}
