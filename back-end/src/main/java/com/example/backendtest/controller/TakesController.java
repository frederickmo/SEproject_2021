package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.UserEntity;
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

    @ApiOperation("按学生ID获取其所有选修课程的ID")
    @GetMapping("/get/student")
    public List<TakesEntity> getAllCoursesByStudentId(Integer studentId) {
        return takesService.getAllCoursesByStudentId(studentId);
    }

    @ApiOperation("按学生ID获取其所有选修课程的详细信息")
    @GetMapping("/get/student/detail")
    public List<CourseEntity> getAllCourseDetailsByStudentId(Integer studentId) {
        return takesService.getAllCourseDetailsByStudentId(studentId);
    }

    @ApiOperation("按课程ID获取其所有选课学生的ID")
    @GetMapping("/get/course")
    public List<TakesEntity> getAllStudentsByCourseId(Integer courseId) {
        return takesService.getAllStudentsByCourseId(courseId);
    }

    @ApiOperation("按课程ID获取其所有选课学生的详细信息")
    @GetMapping("/get/course/detail")
    public List<UserEntity> getAllStudentDetailsByCourseId(Integer courseId) {
        return takesService.getAllStudentDetailsByCourseId(courseId);
    }

    @ApiOperation("学生注册新课程")
    @PostMapping("/add")
    public JSONObject takeNewCourse(@RequestBody TakesEntity takes) {
        return takesService.takeNewCourse(takes);
    }

    @ApiOperation("学生退课")
    @DeleteMapping("/remove")
    public JSONObject removeCourse(Integer studentId, Integer courseId) {
        return takesService.removeCourse(studentId, courseId);
    }
}
