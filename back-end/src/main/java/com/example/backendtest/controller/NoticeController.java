package com.example.backendtest.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.NoticeEntity;
import com.example.backendtest.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;


@Api(tags = "公告管理")
@RestController
@RequestMapping("notice")
@AllArgsConstructor
@CrossOrigin
public class NoticeController {

    private NoticeService noticeService;

    @SaCheckLogin
    @ApiOperation("添加公告")
    @PostMapping("/add")
    public JSONObject addNotice(@RequestBody NoticeEntity notice) throws UnsupportedEncodingException {
          return noticeService.addNotice(notice);
    }

    @SaCheckLogin
    @ApiOperation("按发布时间顺序展示所有的公告(发布时间越近越靠前) - 管理员接口")
    @GetMapping("/get")
    public List<NoticeEntity> showNoticeByTimeDesc()
    {
        return noticeService.showNoticeByTimeDesc();
    }

    @SaCheckLogin
    @ApiOperation("学生获取公告接口")
    @GetMapping("/get/student")
    public List<NoticeEntity> getAllForStudent() {
        return noticeService.getAllForStudent();
    }

    @SaCheckLogin
    @ApiOperation("教师或助教获取公告接口")
    @GetMapping("/get/teacher")
    public List<NoticeEntity> getAllForTeacher() {
        return noticeService.getAllForTeachers();
    }

    @SaCheckLogin
    @ApiOperation("删除公告")
    @DeleteMapping("/remove")
    public JSONObject deleteNotice(int id)
    {
        return noticeService.deleteNotice(id);
    }

    @SaCheckLogin
    @ApiOperation("更改公告")
    @PutMapping("/update")
    public JSONObject deleteNotice(@RequestBody NoticeEntity notice)
    {
        return noticeService.updateNotice(notice);
    }


}
