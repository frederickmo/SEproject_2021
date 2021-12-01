package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.TaskEntity;
import com.example.backendtest.repository.*;
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
public class TaskController {

    private final TaskService taskService;


    @ApiOperation("按实验项目ID查询")
    @GetMapping("/get")
    public TaskEntity getById(Integer id) {
        return taskService.getById(id);
    }

    @ApiOperation("按课程ID查询所有实验项目")
    @GetMapping("/get/course")
    public List<TaskEntity> getByCourseId(Integer courseId) {
        return taskService.getByCourseId(courseId);
    }

    @ApiOperation("添加实验项目")
    @PostMapping("/add")
    public JSONObject addTask(@RequestBody TaskEntity task) {
        return taskService.addTask(task);
    }
}
