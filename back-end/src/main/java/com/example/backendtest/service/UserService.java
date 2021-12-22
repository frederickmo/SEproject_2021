package com.example.backendtest.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.*;
import com.example.backendtest.model.SignEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.repository.SignRepository;
import com.example.backendtest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;

    private SignRepository signRepository;

    public JSONObject add(UserEntity user) {
        // TODO: 总感觉用户注册的检查选项少了什么东西，后续有待完善
        Optional<UserEntity> userTemp = userRepository.findById(user.getId());
        if (userTemp.isPresent()) {
            throw new AlreadyExistException("该用户已存在");
        }
        else if (userRepository.findAllByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("该邮箱已被注册");
        } else {
            // 设置默认值，用户需要的话自己修改

            // 激活状态在注册时默认为未激活(0)
            user.setActivated(0);
            // 性别默认为未知
            user.setGender(0);
            // 身份也默认为未知
            user.setIdentity(0);
            userRepository.save(user);
            log.info("新增用户: 用户ID " + user.getId());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "注册成功！");
            return json;
        }
    }

    public JSONObject remove(Integer id) {
        boolean userExists = userRepository.existsById(id);
        if (!userExists) {
            throw new UserNotFoundException("该用户不存在");
        } else {
            userRepository.deleteById(id);
            log.info("删除用户: ID为 " + id + " 的用户被移除");
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "用户删除成功！");
            return json;
        }
    }

    public int isLogin() {
        if (StpUtil.isLogin()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int isLoginById(Integer id) {
        if (StpUtil.getLoginIdAsInt() == id) {
            return 1;
        } else {
            return 0;
        }
    }


//    public JSONObject login(Integer id, String password) {
//        Optional<UserEntity> userOptional = userRepository.findById(id);
//        if (userOptional.isEmpty()) {
//            throw new UserNotFoundException("该用户不存在");
//        } else {
//            if (userOptional.get().getPassword().equals(password)) {
//                StpUtil.login(id);
//
//                log.info("登陆成功，生成token");
//                String token = JwtUtil.createToken(id);
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("status", 200);
//                    json.put("token", token);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return json;
//
//            } else {
//                throw new PasswordNotCorrectException("密码不正确");
//            }
//        }
//    }

    public SaResult login(Integer id, String password) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户不存在");
        } else {
            if (userOptional.get().getPassword().equals(password)) {
                StpUtil.login(id);
                log.info("用户 " + id + " 登录");
                return SaResult.ok("登陆成功").setData(StpUtil.getTokenInfo());
            } else {
                throw new PasswordNotCorrectException("密码错误");
            }
        }
    }

    public SaResult logout(Integer id) {
        if (StpUtil.getLoginIdAsInt() == id) {
            StpUtil.logout(id);
            return SaResult.ok("成功注销");
        } else {
            return SaResult.error("注销失败");
        }
    }

    public UserEntity getUserById(Integer id) {
//        if (!StpUtil.isLogin()) {
//            throw new NotLoginException();
//        }
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("ID为 " + id + " 的用户不存在");
        } else {
            return userOptional.get();
        }
    }

    public List<UserEntity> getAllTeachers() {
        List<UserEntity> allTeachers = userRepository.findAllTeachers();
        if (allTeachers.isEmpty()) {
            throw new MyNotFoundException("系统中无任何教师或助教");
        } else {
            return allTeachers;
        }
    }

    public List<UserEntity> getAllStudents() {
        List<UserEntity> allStudents = userRepository.findAllStudents();
        if (allStudents.isEmpty()) {
            throw new MyNotFoundException("系统中无任何学生");
        } else {
            return allStudents;
        }
    }

    public List<UserEntity> getAllAdministrators() {
        List<UserEntity> allAdministrators = userRepository.findAllAdministrators();
        if (allAdministrators.isEmpty()) {
            // 这个情况也是有点搞笑，我自己管理员账户调这个接口怎么可能系统中无管理员呢。。。。。
            throw new MyNotFoundException("系统中无任何管理员");
        } else {
            return allAdministrators;
        }
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new MyNotFoundException("系统中无任何用户");
        } else {
            return allUsers;
        }
    }

    public JSONObject updateUserInfo(UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户" + user.getId() + "不存在");
        } else {
            log.info("------------ 用户 " + user.getId() + " 提交信息变更申请 -------------");
            if (user.getGender() == 1 || user.getGender() == 2) {
                userOptional.get().setGender(user.getGender());
                log.info("用户修改性别为 " + user.getGender());
            } else {
                log.info("用户提交的新的性别信息：" + user.getGender());
                log.info("用户未更新性别信息");
            }
            if (user.getName().equals("")) {
                log.info("用户提交的新的姓名信息：" + user.getName());
                log.info("用户未更新姓名信息");
            } else {
                userOptional.get().setName(user.getName());
                log.info("用户修改姓名为 " + user.getName());
            }
            userRepository.save(userOptional.get());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "修改成功");
            return json;
        }
    }

    public JSONObject administratorUpdateUserInfo(UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户不存在");
        } else {
            if (user.getIdentity() != null) {
                userOptional.get().setIdentity(user.getIdentity());
                log.info("管理员修改用户 " + user.getId() + " 身份为 " + user.getIdentity());
            } else {
                log.info("管理员未提交身份变更申请");
            }
            if (user.getActivated() != null) {
                userOptional.get().setActivated(user.getActivated());
                log.info("管理员修改用户 " + user.getId() + " 激活状态为 " + user.getActivated());
            } else {
                log.info("管理员未提交激活状态变更申请");
            }
            userRepository.save(userOptional.get());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("msg", "更新成功");
            return json;
        }
    }

    public JSONObject sign(int userId)
    {
        Optional<SignEntity> signEntity = signRepository.findById(userId);
        if(signEntity.isEmpty())
        {
            Optional<SignEntity> sign = Optional.of(new SignEntity());
            sign.get().setStudentId(userId);
            sign.get().setCount(1);
            sign.get().setUpdateTime(Date.valueOf(LocalDate.now()));
            log.info("学生新增签到信息");
            signEntity = sign;
        }
        else
        {
            if(dateCompare(signEntity.get().getUpdateTime(),Date.valueOf(LocalDate.now()))!=0)
            {
                signEntity.get().setCount(signEntity.get().getCount() + 1);
                signEntity.get().setUpdateTime(Date.valueOf(LocalDate.now()));
                log.info("学生签到信息更新");
            }
        }
        signRepository.save(signEntity.get());
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("msg", "签到成功");
        return json;
    }

    public SignEntity getSign(int userId) {

        Optional<SignEntity> signEntity = signRepository.findById(userId);
        if(signEntity.isEmpty())
        {
            throw new UserNotFoundException("用户没有签到信息");
        }
        else
        {
            return signEntity.get();
        }
    }


    /**
     * 比较日期是否在同一天
     * @param date1
     * @param date2
     * @return
     */
    public static int dateCompare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFirst = dateFormat.format(date1);
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal < dateLastIntVal) {
            return -1;
        }
        return 0;
    }

    }


