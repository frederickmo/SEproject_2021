package com.example.backendtest.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.NoticeEntity;
import com.example.backendtest.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;


@Api(tags = "公告管理")
@RestController
@RequestMapping("topic")
@AllArgsConstructor
public class NoticeController {

    private NoticeService noticeService;

    @ApiOperation("添加公告")
    @PostMapping("/addNotice")
    public JSONObject addNotice(int postedId, String topic, String content, int type, Date updatedTime) throws UnsupportedEncodingException {
          return noticeService.addNotice(postedId,topic,content,type,updatedTime);
    }

    @ApiOperation("按时间顺序展示所有的公告")
    @GetMapping("/showNoticeByTime")
    public List<NoticeEntity> showNoticeByTime()
    {
        return noticeService.showNoticeByTime();
    }

    @ApiOperation("删除公告")
    @PostMapping("/deleteNotice")
    public JSONObject deleteNotice(String topic)
    {
        return noticeService.deleteNotice(topic);
    }

    @ApiOperation("更改公告")
    @PostMapping("/updateNoticeById")
    public JSONObject deleteNotice(int Id,int postedId, String topic, String content, int type, Date updatedTime)
    {
        return noticeService.updateNotice(Id,postedId,topic,content,type,updatedTime);
    }
    
}
