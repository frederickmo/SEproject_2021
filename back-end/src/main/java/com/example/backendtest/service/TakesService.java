package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.TakesEntityPK;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.repository.CourseRepository;
import com.example.backendtest.repository.TakesRepository;
import com.example.backendtest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * 按学生ID查找其选修的所有课程
     * @param studentId 学生ID
     * @return 该学生选修所有课程
     */
    public List<Integer> getAllByStudentId(Integer studentId) {
        Optional<List<Integer>> coursesOptional = takesRepository.findAllByStudentId(studentId);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("学号为 " + " 的学生没有选修任何实验课程！");
        } else {
            return coursesOptional.get();
        }
    }

    /**
     * 按课程ID查找所有选课学生
     * @param courseId 课程ID
     * @return 所有选课学生
     */
    public List<Integer> getAllByCourseId(Integer courseId) {
        Optional<List<Integer>> studentsOptional = takesRepository.findAllByCourseId(courseId);
        if (studentsOptional.isEmpty()) {
            throw new IllegalStateException("ID为 " + courseId + " 的课程没有学生选修！");
        } else {
            return studentsOptional.get();
        }
    }

    /**
     * 学生新增选课
     * @param takes
     * @return
     */
    public JSONObject takeNewCourse(TakesEntity takes) {
        Integer studentId = takes.getStudentId();
        Integer courseId = takes.getCourseId();
        Optional<TakesEntity> takesOptional = takesRepository.findById(studentId, courseId);
        Optional<UserEntity> studentOptional = userRepository.findById(studentId);
        Optional<CourseEntity> courseOptional = courseRepository.findById(courseId);
        log.info("takesOptional在takes表里找到学生id="
                + studentId + ",课程id="
                + courseId + "的课了吗 => "
                + takesOptional.isPresent());
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
}
