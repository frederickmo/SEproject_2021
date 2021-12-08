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
    public JSONObject add(@RequestBody CourseEntity course) {
        return courseService.add(course);
    }

    @ApiOperation("删除课程【注意：此操作会将所有该课程的学生选课信息一并移除！】")
    @DeleteMapping("/remove")
    public JSONObject remove(Integer courseId) {
        return courseService.remove(courseId);
    }


    @ApiOperation("修改课程信息")
    @PutMapping("/updateCourse")
    public JSONObject updateCourse(@RequestBody CourseEntity course){return courseService.update(course);}

}
