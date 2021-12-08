package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.service.CourseService;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.service.ManagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "教师授课信息管理")
@RestController
@RequestMapping("manages")
@AllArgsConstructor
public class ManagesController {

    private final ManagesService managesService;

    @ApiOperation("增加老师/助教执教课程关系")
    @PostMapping("/add")
    public JSONObject addTeacherManages(Integer teacherId,Integer courseId)
    {
        return managesService.addTeacherManages(teacherId,courseId);
    }

    @ApiOperation("删除老师/助教执教课程关系")
    @GetMapping("/delete")
    public JSONObject deleteTeacherManages(Integer teacherId,Integer courseId)
    {
        return managesService.deleteTeacherManages(teacherId,courseId);
    }

    @ApiOperation("按教师ID获取其管理所有课程")
    @GetMapping("/get/teacher")
    public List<ManagesEntity> getAllByTeacherId(Integer teacherId) {
        return managesService.getAllByTeacherId(teacherId);
    }

    @ApiOperation("按课程ID获取所有任课教师")
    @GetMapping("/get/course")
    public List<ManagesEntity> getAllByCourseId(Integer courseId) {
        return managesService.getAllByCourseId(courseId);
    }

    @ApiOperation("按教师ID获取其管理所有课程详细信息")
    @GetMapping("/get/teacher/detail")
    public List<CourseEntity> getAllByTeacherIdInDetail(Integer teacherId) {
        return managesService.getAllByTeacherIdInDetail(teacherId);
    }

    @ApiOperation("按课程ID获取所有任课教师详细信息")
    @GetMapping("/get/course/detail")
    public List<UserEntity> getAllByCourseIdInDetail(Integer courseId) {
        return managesService.getAllByCourseIdInDetail(courseId);
    }
}
