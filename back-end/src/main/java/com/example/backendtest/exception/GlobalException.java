package com.example.backendtest.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.backendtest.util.AjaxJson;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalException {

    // 全局异常拦截（拦截项目中的所有异常）
    @ResponseBody
    @ExceptionHandler
    public AjaxJson handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 打印堆栈，以供调试
        System.out.println("全局异常---------------");
        e.printStackTrace();

        // 不同异常返回不同状态码
        AjaxJson aj = null;
        if (e instanceof NotLoginException) {	// 如果是未登录异常
            NotLoginException ee = (NotLoginException) e;
            aj = AjaxJson.getNotLogin().setMsg(ee.getMessage());
        }
        else if(e instanceof NotRoleException) {		// 如果是角色异常
            NotRoleException ee = (NotRoleException) e;
            aj = AjaxJson.getNotJur("无此角色：" + ee.getRole());
        }
        else if(e instanceof NotPermissionException) {	// 如果是权限异常
            NotPermissionException ee = (NotPermissionException) e;
            aj = AjaxJson.getNotJur("无此权限：" + ee.getCode());
        }
        else if(e instanceof DisableLoginException) {	// 如果是被封禁异常
            DisableLoginException ee = (DisableLoginException) e;
            aj = AjaxJson.getNotJur("账号被封禁：" + ee.getDisableTime() + "秒后解封");
        }
        else if (e instanceof MyNotFoundException) {
            MyNotFoundException ee = (MyNotFoundException) e;
            aj = AjaxJson.getMyNotFound(ee.getMessage());
        }
        else if (e instanceof AlreadyExistException) {
            AlreadyExistException ee = (AlreadyExistException) e;
            aj = AjaxJson.getAlreadyExist(ee.getMessage());
        }
        else if (e instanceof UserNotFoundException) {
            UserNotFoundException ee = (UserNotFoundException) e;
            aj = AjaxJson.getUserNotFound(ee.getMessage());
        }
        else if (e instanceof CourseNotFoundException) {
            CourseNotFoundException ee = (CourseNotFoundException) e;
            aj = AjaxJson.getCourseNotFound(ee.getMessage());
        }
        else if (e instanceof TaskNotFoundException) {
            TaskNotFoundException ee = (TaskNotFoundException) e;
            aj = AjaxJson.getTaskNotFound(ee.getMessage());
        }
        else if (e instanceof StudentNotFoundException) {
            StudentNotFoundException ee = (StudentNotFoundException) e;
            aj = AjaxJson.getStudentNotFound(ee.getMessage());
        }
        else if (e instanceof TeacherNotFoundException) {
            TeacherNotFoundException ee = (TeacherNotFoundException) e;
            aj = AjaxJson.getTeacherNotFound(ee.getMessage());
        }
        else if (e instanceof EmailAlreadyRegisteredException) {
            EmailAlreadyRegisteredException ee = (EmailAlreadyRegisteredException) e;
            aj = AjaxJson.getEmailAlreadyRegistered(ee.getMessage());
        }
        else if (e instanceof FailInReadingSubmitInfoException) {
            FailInReadingSubmitInfoException ee = (FailInReadingSubmitInfoException) e;
            aj = AjaxJson.getFailInReadingSubmitInfo(ee.getMessage());
        }
        else if (e instanceof FileNameIllegalException) {
            FileNameIllegalException ee = (FileNameIllegalException) e;
            aj = AjaxJson.getFileNameIllegal(ee.getMessage());
        }
        else if (e instanceof FileStorageException) {
            FileStorageException ee = (FileStorageException) e;
            aj = AjaxJson.getFileStorage(ee.getMessage());
        }
        else if (e instanceof MyFileNotFoundException) {
            MyFileNotFoundException ee = (MyFileNotFoundException) e;
            aj = AjaxJson.getMyFileNotFound(ee.getMessage());
        }
        else if (e instanceof PasswordNotCorrectException) {
            PasswordNotCorrectException ee = (PasswordNotCorrectException) e;
            aj = AjaxJson.getPasswordNotCorrect(ee.getMessage());
        }
        else if (e instanceof RoleNotMatchException) {
            RoleNotMatchException ee = (RoleNotMatchException) e;
            aj = AjaxJson.getRoleNotMatch(ee.getMessage());
        }
        else {	// 普通异常, 输出：500 + 异常信息
            aj = AjaxJson.getError(e.getMessage());
        }

        // 返回给前端
        return aj;
    }

}
