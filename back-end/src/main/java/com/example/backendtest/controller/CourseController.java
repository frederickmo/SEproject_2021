package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "课程管理")
@RestController
@RequestMapping("course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @ApiOperation("按ID获取课程信息")
    @GetMapping("/get")
    public CourseEntity getById(Integer id) {
        return courseService.getById(id);
    }

    @ApiOperation("按课程名获取课程信息")
    @GetMapping("/getByName")
    public List<CourseEntity> getAllByName(String name) {
        return courseService.getAllByName(name);
    }

    @ApiOperation("按学年获取课程信息")
    @GetMapping("/getByYear")
    public List<CourseEntity> getAllByYear(Integer year) {
        return courseService.getAllByYear(year);
    }

    @ApiOperation("按学年和学期获取课程信息")
    @GetMapping("/getByYearAndSemester")
    public List<CourseEntity> getAllByYearAndSemester(Integer year, Integer semester) {
        return courseService.getAllByYearAndSemester(year, semester);
    }

    @ApiOperation("新增课程")
    @PostMapping("/add")
    public JSONObject addCourse(@RequestBody CourseEntity course) {
        return courseService.addCourse(course);
    }
}
