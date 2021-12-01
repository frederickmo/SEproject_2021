package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.service.TakesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学生选课管理")
@RestController
@RequestMapping("takes")
@AllArgsConstructor
public class TakesController {

    private final TakesService takesService;

    @ApiOperation("按学生ID获取其选修所有课程")
    @GetMapping("/get/student")
    public List<Integer> getAllByStudentId(Integer studentId) {
        return takesService.getAllByStudentId(studentId);
    }

    @ApiOperation("按课程ID获取所有选课学生ID")
    @GetMapping("/get/course")
    public List<Integer> getAllByCourseId(Integer courseId) {
        return takesService.getAllByCourseId(courseId);
    }

    @ApiOperation("学生注册新课程")
    @PostMapping("/add")
    public JSONObject takeNewCourse(@RequestBody TakesEntity takes) {
        return takesService.takeNewCourse(takes);
    }
}
