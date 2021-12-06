package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.service.FileStorageService;
import com.example.backendtest.service.FinishesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Api(tags = "作业管理")
@RestController
@RequestMapping("finishes")
@AllArgsConstructor
public class FinishesController {

//    @Autowired
    private FinishesService finishesService;

    private FileStorageService fileStorageService;

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
        return finishesService.checkStudentForTask(studentId,taskId);
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
    public JSONObject uploadHomework(Integer studentId, Integer taskId,String url)
    {
        return finishesService.uploadHomework(studentId,taskId,url);
    }


    @ApiOperation("查询某项实验报告是否提交")
    @GetMapping("/isFinished")
    public boolean isTaskFinished(Integer studentId, Integer taskId) {
        return finishesService.isTaskFinished(studentId, taskId);
    }

    @ApiOperation("提交在线实验报告")
    @PostMapping("/upload/online")
    public JSONObject submitOnlineTask(@RequestBody JSONObject taskBody) {
        return finishesService.submitOnlineTask(taskBody);
    }

    @ApiOperation("提交大型实验报告")
    @PostMapping("/upload/complex")
    public JSONObject submitComplexTask(@RequestParam("file")MultipartFile file,
                                        Integer studentId,
                                        Integer taskId,
                                        String newFileName,
                                        String location) {
        return finishesService.submitComplexTask(file, studentId, taskId, newFileName, location);
    }

    @ApiOperation("获取所有提交记录")
    @GetMapping("/get/record")
    public List<FinishesEntity> getAllSubmitRecords(Integer studentId) {
        return finishesService.getAllSubmitRecords(studentId);
    }

    @ApiOperation("获取所有提交记录以及得分情况（若有）以及课程和实验项目的详细信息")
    @GetMapping("/get/record/detail")
    public List<Object> getAllScoresOfSubmitRecordsInDetail(Integer studentId) {
        return finishesService.getAllScoresOfSubmitRecordsInDetail(studentId);
    }
}
