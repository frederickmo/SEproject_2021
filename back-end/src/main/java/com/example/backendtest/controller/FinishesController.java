package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.FileStorageService;
import com.example.backendtest.service.FinishesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "作业管理")
@RestController
@RequestMapping("finishes")
@AllArgsConstructor
@CrossOrigin
public class FinishesController {

//    @Autowired
    private FinishesService finishesService;

    private FileStorageService fileStorageService;

    /**
     * 这个接口可以保证学生，课程，任务三者是相对的才可以提交
     */
//    @SaCheckLogin
//    @ApiOperation("检查学生是否有权利上传该task")
//    @GetMapping("/checkStudentForTask")
//    public JSONObject checkStudentForTask(Integer studentId, Integer taskId)
//    {
//        return finishesService.checkStudentForTask(studentId,taskId);
//    }

    /**
     * 把上传作业记录到数据库中
     */
//    @SaCheckLogin
//    @ApiOperation("学生上传作业")
//    @PostMapping("/uploadHomework")
//    public JSONObject uploadHomework(Integer studentId, Integer taskId,String url)
//    {
//        return finishesService.uploadHomework(studentId,taskId,url);
//    }

    @SaCheckLogin
    @ApiOperation("查询某项实验报告是否提交")
    @GetMapping("/isFinished")
    public boolean isTaskFinished(Integer studentId, Integer taskId) {
        return finishesService.isTaskFinished(studentId, taskId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID和项目ID查询具体信息")
    @GetMapping("/get")
    public FinishesEntity getByStudentIdAndTaskId(Integer studentId, Integer taskId) {
        return finishesService.getByStudentIdAndTaskId(studentId, taskId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID和项目ID获取原json文件")
    @GetMapping("/get/onlineReport")
    public JSONObject getOnlineReportJsonFile(Integer studentId, Integer taskId) {
        return finishesService.getOnlineTaskJsonFile(studentId, taskId);
    }

    @SaCheckLogin
    @ApiOperation("提交在线实验报告")
    @PostMapping("/upload/online")
    public JSONObject submitOnlineTask(@RequestBody JSONObject taskBody) {
        return finishesService.submitOnlineTask(taskBody);
    }

    @SaCheckLogin
    @ApiOperation("提交大型实验报告")
    @PostMapping("/upload/complex")
    public JSONObject submitComplexTask(@RequestParam("file")MultipartFile file,
                                        Integer studentId,
                                        Integer taskId,
                                        String location) {
        return finishesService.submitComplexTask(file, studentId, taskId, location);
    }

    @SaCheckLogin
    @ApiOperation("获取所有提交记录")
    @GetMapping("/get/record")
    public List<FinishesEntity> getAllSubmitRecords(Integer studentId) {
        return finishesService.getAllSubmitRecords(studentId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID获取所有提交记录以及得分情况（若有）以及课程和实验项目的详细信息")
    @GetMapping("/get/record/detail/student")
    public List<Object> getAllScoresOfSubmitRecordsInDetail(Integer studentId) {
        return finishesService.getAllScoresOfSubmitRecordsByStudentIdInDetail(studentId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID和课程ID获取所有提交记录以及得分情况（若有）以及课程和实验项目的详细信息")
    @GetMapping("/get/record/detail/studentAndCourse")
    public List<Object> getAllScoresOfSubmitRecordsByStudentIdAndCourseIdInDetail(Integer studentId, Integer courseId) {
        return finishesService.getAllScoresOfSubmitRecordsByStudentIdAndCourseIdInDetail(studentId, courseId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID获取所有提交记录以及得分情况（若有）以及课程和实验项目的详细信息（按课程ID聚集）")
    @GetMapping("/get/record/detail/student/groupByCourse")
    public List<List<Object>> getAllScoresOfSubmitRecordsByStudentIdGroupByCourseIdInDetail(Integer studentId) {
        return finishesService.getAllScoresOfSubmitRecordsByStudentIdGroupByCourseIdInDetail(studentId);
    }

    @SaCheckLogin
    @ApiOperation("按项目ID获取所有已经提交的学生的信息")
    @GetMapping("/get/record/detail/task/finished")
    public List<Object> getAllFinishedRecordsByTaskId(Integer taskId) {
        return finishesService.getAllFinishedRecordsByTaskId(taskId);
    }

    @SaCheckLogin
    @ApiOperation("按项目ID获取所有未提交已给分的学生信息")
    @GetMapping("/get/record/detail/task/unfinished/scoreGiven")
    public List<Object> getAllUnfinishedRecordsScoreGivenByTaskId(Integer taskId){
        return finishesService.getAllUnfinishedRecordsScoreGivenByTaskId(taskId);
    }

    @SaCheckLogin
    @ApiOperation("按项目ID获取所有未提交未给分的学生实体(UserEntity)")
    @GetMapping("/get/record/detail/task/unfinished/scoreNotGiven")
    public List<UserEntity> getAllUnfinishedRecordsScoreNotGivenByTaskId(Integer taskId) {
        return finishesService.getAllUnfinishedRecordsScoreNotGivenByTaskId(taskId);
    }
}
