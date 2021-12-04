package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.service.FinishesSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "作业管理")
@RestController
@RequestMapping("finishes")
public class FinishesController {

    @Autowired
    private  FinishesSerivce finishesSerivce;

    /**
     * 这个接口可以保证学生，课程，任务三者是相对的才可以提交
     * @param studentId
     * @param taskId
     * @return
     */
    @ApiOperation("检查学生是否有权利上传该task")
    @PostMapping("/checkStudentForTask")
    public JSONObject checkStudentForTask(Integer studentId, Integer taskId)
    {
        return finishesSerivce.checkStudentForTask(studentId,taskId);
    }

    /**
     * 把上传作业记录到数据库中
     * @param studentId
     * @param taskId
     * @param url
     * @return
     */
    @ApiOperation("学生上传作业")
    @PostMapping("/uploadHomework")
    public JSONObject uploadHomework(Integer studentId,Integer taskId,String url)
    {
        return finishesSerivce.uploadHomework(studentId,taskId,url);
    }


}
