package com.example.backendtest.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.*;
import com.example.backendtest.model.SignEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.model.VerificationCode;
import com.example.backendtest.repository.SignRepository;
import com.example.backendtest.repository.UserRepository;
import com.example.backendtest.repository.VerificationCodeRepository;
import com.example.backendtest.util.VerifyEmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private VerificationCodeRepository verificationCodeRepository;

    private SignRepository signRepository;
    private VerifyEmailUtil verifyEmailUtil;

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
            // 身份默认为学生
            user.setIdentity(1);
            if (user.getName().equals("") || user.getName() == null) {
                user.setName("未知");
            }
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

    /**
     * 查看当前登录状态
     */
    public int isLogin() {
        if (StpUtil.isLogin()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 查看某ID是否登录
     */
    public int isLoginById(Integer id) {
        if (StpUtil.getLoginIdAsInt() == id) {
            return 1;
        } else {
            return 0;
        }
    }

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
                throw new NotCorrectException("密码错误");
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
            if (user.getEmail().equals("")) {
                log.info("用户提交的新的email信息：" + user.getEmail());
                log.info("用户未更新email信息");
            } else {
                Optional<UserEntity> allByEmail = userRepository.findAllByEmail(user.getEmail());
                if (allByEmail.isPresent()) {
                    throw new EmailAlreadyRegisteredException("该邮箱已被注册");
                } else {
                    userOptional.get().setEmail(user.getEmail());
                    log.info("用户修改email为 " + user.getEmail());
                }
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
            } else {
                throw new AlreadyExistException("今日已签到");
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
            throw new MyNotFoundException("用户没有签到信息");
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


    /**
     * 用户重置密码步骤1 - 发送验证邮件
     */
    public JSONObject verifyPasswordAndSendVerificationEmail(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户不存在");
        } else if (userOptional.get().getEmail() == null || userOptional.get().getEmail().equals("")) {
            throw new MyNotFoundException("用户邮箱不存在，请提交一个邮箱再重置密码");
        } else {
            String email = userOptional.get().getEmail();
            String verifyCode = verifyEmailUtil.getPhoneCode();
            verifyEmailUtil.sendCode2Email(email, "【实验教学管理系统】重置密码", verifyCode);
            log.info("用户 " + userId + " 请求重置密码：邮件已发送。");
            if (verificationCodeRepository.existsById(userId)) {
                verificationCodeRepository.deleteById(userId);
            }
            VerificationCode record = new VerificationCode();
            record.setUserId(userId);
            record.setCode(verifyCode);
            Timestamp expiresTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(30));
            log.info("失效时间：" + expiresTime);
            record.setExpiresAt(expiresTime);
            verificationCodeRepository.save(record);
            JSONObject result = new JSONObject();
            result.put("code", 200);
            result.put("msg", "密码验证成功，邮件已发送");
            return result;
        }
    }

    /**
     * 用户重置密码步骤2 - 验证邮件验证码
     */
    public JSONObject checkVerificationCode(Integer userId, String code) {
        Optional<VerificationCode> recordOptional = verificationCodeRepository.findById(userId);
        if (recordOptional.isEmpty()) {
            throw new MyNotFoundException("没有验证码发送记录");
        } else if (Timestamp.valueOf(LocalDateTime.now()).after(recordOptional.get().getExpiresAt())) {
            throw new ExpiredException("验证码已过期");
        } else if (!recordOptional.get().getCode().equals(code)) {
            throw new NotCorrectException("验证码错误");
        } else {
            log.info("用户 " + userId + " 正在重置密码：验证码验证成功。");
            // 验证成功，删除该验证码记录
            verificationCodeRepository.deleteById(userId);
            JSONObject result = new JSONObject();
            result.put("code", 200);
            result.put("msg", "验证码正确，请提交新的密码");
            return result;
        }
    }

    /**
     * 用户重置密码步骤3 - 更新密码
     */
    public JSONObject updatePassword(Integer userId, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户不存在");
        } else {
            String oldPassword = userOptional.get().getPassword();
            if (newPassword.equals(oldPassword)) {
                throw new AlreadyExistException("新密码与旧密码相同");
            }
            userOptional.get().setPassword(newPassword);
            // 修改完记得存
            userRepository.save(userOptional.get());
            log.info("用户 " + userId + " 已成功重置密码。");
            JSONObject result = new JSONObject();
            result.put("code", 200);
            result.put("msg", "密码已重置");
            return result;
        }
    }

    /**
     * 找回密码：根据用户ID找回密码
     */
    public String recoverPassword(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("用户不存在");
        } else {
            return userOptional.get().getPassword();
        }
    }


}


