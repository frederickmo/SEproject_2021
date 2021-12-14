package com.example.backendtest.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@AllArgsConstructor
public class VerifyEmailUtil {


    SendEmailUtil sendEmailUtil;
    TemplateEngine templateEngine;

    //随机生成大写英文字母
    private char getRandomCapitalLetter() {
        int i = (int) (Math.random() * 25);
        //大写字母的ASCII值为65-90
        int j = i + 65;
        char letter = (char) j;
        return letter;
    }

    /**
     * 随机生成6位验证码：大写英文字符 + 数字
     * @return 验证码
     */
    public String getEmailCode() {
        StringBuilder code = new StringBuilder();
        int dir = 0;
        for (int i = 0; i < 8; i++) {
            dir = (Math.random() > 0.5) ? 1 : 0;
            if (dir == 1) {
                //拼数字
                code.append((int) (Math.random() * 9));
            } else {
                //拼字母
                code.append(getRandomCapitalLetter());
            }
        }
        return code.toString();
    }

    /**
     * 随机生成6位验证码，全部为数字组成。
     * @return 验证码
     */
    public String getPhoneCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 9));
        }
        return code.toString();
    }

    /**
     * 发送验证码至相应邮箱
     * @param to 收件人
     * @param title 主题
     * @param verifyCode 内容
     */

    public void sendCode2Email(String to, String title, String verifyCode) {
        //把验证码放到thymeleaf模板中
        Context context = new Context();
        context.setVariable("verifyCode", verifyCode);
        String emailContent = templateEngine.process("emailTemplate", context);
        //发送邮件
        sendEmailUtil.sendHtmlMail(to, title, emailContent);
    }


    // VerifyEmailService verifyEmailService;

    /**
     * 发送邮件并把验证码放入session
     * @param request
     * @param email
     * @return
     */
    public JSONObject sendMail(HttpServletRequest request, String email)
    {
        String verifyCode = getEmailCode();
        sendCode2Email(email, "SimpleTest", verifyCode);
        HttpSession session = request.getSession(false);
        if (session == null) {
            //创建session，并拿到JSESSIONID
            session = request.getSession();
        }
        //设置session有效时间，默认是1800s
        session.setMaxInactiveInterval(900);
        //将验证码放入session中
        session.setAttribute("verifyCode", verifyCode);
        JSONObject json = new JSONObject();
        json.put("status", 200);
        json.put("message", "成功发送");
        return json;
    }

    /**
     * 检查验证码
     * @param inputCode
     * @param request
     * @return
     */
    public JSONObject checkCode(String inputCode, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String verifyCode = (String) session.getAttribute("verifyCode");
        if (inputCode.equals(verifyCode)){
            //主动使session无效
            session.invalidate();
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "验证码正确");
            return json;
        }
        JSONObject json = new JSONObject();
        json.put("status", 500);
        json.put("message", "验证码错误");
        return json;
    }

}
