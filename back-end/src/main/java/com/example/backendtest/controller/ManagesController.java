package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.service.CourseService;
import com.example.backendtest.service.ManagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "课程管理")
@RestController
@RequestMapping("manages")
@AllArgsConstructor
public class ManagesController {

    private final ManagesService managesService;

    @ApiOperation("增加老师/助教执教课程关系")
    @PostMapping("/addTeacherManages")
    public JSONObject addTeacherManages(Integer teacherId,Integer courseId)
    {
        return managesService.addTeacherManages(teacherId,courseId);
    }

    @ApiOperation("删除老师/助教执教课程关系")
    @DeleteMapping("/deleteTeacherManages")
    public JSONObject deleteTeacherManages(Integer teacherId,Integer courseId)
    {
        return managesService.deleteTeacherManages(teacherId,courseId);
    }

    @ApiOperation("通过课程号得到所有的执教教师/助教的id")
    @PostMapping("/showAllIdByCourseId")
    public List<ManagesEntity> showAllIdByCourseId(Integer courseId)
    {
        return managesService.showAllIdByCourseId(courseId);
    }
    


}
