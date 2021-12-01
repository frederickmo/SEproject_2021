package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseEntity getById(Integer id) {
        Optional<CourseEntity> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            throw new IllegalStateException("ID为 " + id + " 的课程不存在！");
        } else {
            return courseOptional.get();
        }
    }

    public List<CourseEntity> getAllByName(String name) {
        Optional<List<CourseEntity>> coursesOptional = courseRepository.findAllByName(name);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("没有名称为 " + name + " 的课程！");
        } else {
            return coursesOptional.get();
        }
    }

    public List<CourseEntity> getAllByYear(Integer year) {
        Optional<List<CourseEntity>> coursesOptional = courseRepository.findAllByYear(year);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("没有课程在 " + year + " 年开课！");
        } else {
            return coursesOptional.get();
        }
    }

    public List<CourseEntity> getAllByYearAndSemester(Integer year, Integer semester) {
        Optional<List<CourseEntity>> coursesOptional = courseRepository.findAllByYearAndSemester(year, semester);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("没有课程在 " + year + " 年第 " + semester + " 学期开课！");
        } else {
            return coursesOptional.get();
        }
    }

    public JSONObject addCourse(CourseEntity course) {
        Optional<CourseEntity> courseOptional = courseRepository.findById(course.getId());
        if (courseOptional.isPresent()) {
            throw new IllegalStateException("该课程已存在");
        } else {
            courseRepository.save(course);
            log.info("新增课程 => 课程ID: " + course.getId() + " 课程名: " + course.getName());
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "课程添加成功");
            return json;
        }
    }
}
