package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.TaskEntity;
import com.example.backendtest.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "实验项目管理")
@RestController
@RequestMapping("task")
@AllArgsConstructor
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    @SaCheckLogin
    @ApiOperation("按实验项目ID查询")
    @GetMapping("/get")
    public TaskEntity getById(Integer id) {
        return taskService.getByTaskId(id);
    }

    @SaCheckLogin
    @ApiOperation("按课程ID查询所有实验项目")
    @GetMapping("/get/course")
    public List<TaskEntity> getByCourseId(Integer courseId) {
        return taskService.getByCourseId(courseId);
    }

    @SaCheckLogin
    @ApiOperation("按课程ID查询小型实验项目")
    @GetMapping("/get/simple")
    public List<TaskEntity> getSimpleTask(Integer courseId) {
        return taskService.getSimpleTask(courseId);
    }

    @SaCheckLogin
    @ApiOperation("按课程ID查询大型实验项目")
    @GetMapping("/get/complex")
    public List<TaskEntity> getComplexTask(Integer courseId) {
        return taskService.getComplexTask(courseId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID获取所有未完成的实验项目的信息（按截止日期升序排列，截止日期为空置于最后）")
    @GetMapping("/getAll/unfinished/deadline/asc")
    public List<Object> getAllByStudentIdAndUnfinishedOrderByDeadlineAsc(Integer studentId) {
        return taskService.getAllByStudentIdAndUnfinishedOrderByDeadlineAsc(studentId);
    }

    @SaCheckLogin
    @ApiOperation("按学生ID获取所有已完成的实验项目的信息（按截止日期升序排列，截止日期为空置于最后）")
    @GetMapping("/getAll/finished/deadline/asc")
    public List<Object> getAllByStudentIdAndFinishedOrderByDeadlineAsc(Integer studentId) {
        return taskService.getAllByStudentIdAndFinishedOrderByDeadlineAsc(studentId);
    }

    @SaCheckLogin
    @ApiOperation("添加实验项目")
    @PostMapping("/add")
    public JSONObject add(@RequestBody TaskEntity task) {
        return taskService.add(task);
    }

    @SaCheckLogin
    @ApiOperation("删除实验项目")
    @DeleteMapping("/remove")
    public JSONObject remove(Integer taskId) {
        return taskService.remove(taskId);
    }

    @SaCheckLogin
    @ApiOperation("根据项目id修改项目的详细信息")
//    @PostMapping("/updateTaskInformation")
    @PutMapping("/update")
    public JSONObject updateTaskInformation(@RequestBody TaskEntity taskEntity)
    {
        return taskService.updateTaskInformation(taskEntity);
    }


}
