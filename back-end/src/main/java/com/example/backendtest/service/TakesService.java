package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.repository.CourseRepository;
import com.example.backendtest.repository.TakesRepository;
import com.example.backendtest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TakesService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TakesRepository takesRepository;

    /**
     * 按学生ID查找其选修的所有课程(返回TakesEntity列表)
     * @param studentId 学生ID
     * @return 含有某一学生ID的TakesEntity实体列表
     */
    public List<TakesEntity> getAllCoursesByStudentId(Integer studentId) {
        Optional<List<TakesEntity>> coursesOptional = takesRepository.findAllByStudentId(studentId);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("学号为 " + " 的学生没有选修任何实验课程！");
        } else {
            return coursesOptional.get();
        }
    }

    /**
     * 按课程ID查找所有选课学生(返回TakesEntity列表)
     * @param courseId 课程ID
     * @return 含有某一课程ID的TakesEntity列表
     */
    public List<TakesEntity> getAllStudentsByCourseId(Integer courseId) {
        Optional<List<TakesEntity>> studentsOptional = takesRepository.findAllByCourseId(courseId);
        if (studentsOptional.isEmpty()) {
            throw new IllegalStateException("ID为 " + courseId + " 的课程没有学生选修！");
        } else {
            return studentsOptional.get();
        }
    }

    /**
     * /getAllCoursesByStudentId()/返回的是TakesEntity的列表，此处为前端调用方便也可返回CourseEntity的列表
     * @param studentId 学生ID
     * @return CourseEntity列表
     */
    public List<CourseEntity> getAllCourseDetailsByStudentId(Integer studentId) {
        List<TakesEntity> takesList = getAllCoursesByStudentId(studentId);
        List<CourseEntity> courseList = new ArrayList<>();
        for (TakesEntity takes : takesList) {
            Optional<CourseEntity> courseOptional = courseRepository.findById(takes.getCourseId());
            if (courseOptional.isEmpty()) {
                throw new IllegalStateException("该学生的选课列表中，ID为 " + takes.getCourseId() + " 的课程不存在！");
            } else {
                courseList.add(courseOptional.get());
            }
        }
        return courseList;
    }

    /**
     * /getAllStudentsByCourseId()/返回的是TakesEntity的列表，此处为前端调用方便也可返回UserEntity的列表
     * @param courseId 课程ID
     * @return UserEntity列表
     */
    public List<UserEntity> getAllStudentDetailsByCourseId(Integer courseId) {
        List<TakesEntity> takesList = getAllStudentsByCourseId(courseId);
        List<UserEntity> studentList = new ArrayList<>();
        for (TakesEntity takes : takesList) {
            Optional<UserEntity> studentOptional = userRepository.findById(takes.getStudentId());
            if (studentOptional.isEmpty()) {
                throw new IllegalStateException("该课程的选课学生中，ID为 " + takes.getStudentId() + " 的学生不存在！");
            } else {
                studentList.add(studentOptional.get());
            }
        }
        return studentList;
    }

    /**
     * 学生新增选课
     * @param takes 学生ID+选课ID构成的TakesEntity
     * @return 选课结果json
     */
    public JSONObject takeNewCourse(TakesEntity takes) {
        Integer studentId = takes.getStudentId();
        Integer courseId = takes.getCourseId();
        Optional<TakesEntity> takesOptional = takesRepository.findById(studentId, courseId);
        Optional<UserEntity> studentOptional = userRepository.findById(studentId);
        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);
//        log.info("takesOptional在takes表里找到学生id="
//                + studentId + ",课程id="
//                + courseId + "的课了吗 => "
//                + takesOptional.isPresent());
        if (studentOptional.isEmpty()) {
            throw new IllegalStateException("ID为 " + studentId + " 的学生不存在！");
        } else if (courseOptional.isEmpty()) {
            throw new IllegalStateException("ID为" + courseId + " 的课程不存在！");
        } else if (takesOptional.isPresent()) {
            throw new IllegalStateException("该学生已经选修该实验课程！");
        }
        // TODO: 能不能把有关数据库查询的异常返回前端的错误码从500: Internal Server Error 改成别的？
        else {
                takesRepository.save(takes);
                log.info("新增选课信息： 课程ID: "
                        + takes.getCourseId()
                        + " 新增选课学生 ID: "
                        + takes.getStudentId());
                JSONObject json = new JSONObject();
                json.put("status", 200);
                json.put("message", "新增选课成功");
                return json;
        }
    }

    public JSONObject removeCourse(Integer studentId, Integer courseId) {
        boolean studentExists = userRepository.existsById(studentId);
        boolean courseExists = courseRepository.existsById(courseId);
        if (!studentExists) {
            throw new IllegalStateException("该学生不存在！");
        } else if (!courseExists) {
            throw new IllegalStateException("该课程不存在！");
        } else {
            takesRepository.remove(studentId, courseId);
            log.info("新增退课信息： ID为 " + studentId + " 的学生退掉了ID为 " + courseId + " 的课程");
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "退课成功");
            return json;
        }
    }

    public JSONObject checkExist(Integer id, Integer courseId) {

        Optional<TakesEntity> takesTemp = takesRepository.findById(id,courseId);
        if (takesTemp.isPresent())
        {
            JSONObject json = new JSONObject();
            json.put("status", 000);
            json.put("message", "学生选课关系已存在");
            json.put("boolean",true);
            return json;
        }
        JSONObject json = new JSONObject();
        json.put("status", 001);
        json.put("message", "学生选课关系不存在");
        json.put("boolean",false);
        return json;
    }
}
