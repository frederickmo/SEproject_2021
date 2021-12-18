package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.StudentNotFoundException;
import com.example.backendtest.exception.TaskNotFoundException;
import com.example.backendtest.exception.TeacherNotFoundException;
import com.example.backendtest.model.ChecksEntity;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.repository.ChecksRepository;
import com.example.backendtest.repository.FinishesRepository;
import com.example.backendtest.repository.TaskRepository;
import com.example.backendtest.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ChecksService {

    private final ChecksRepository checksRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final FinishesRepository finishesRepository;

    public JSONObject add(ChecksEntity checks) {
        if (!userRepository.existsById(checks.getTeacherId())) {
            throw new TeacherNotFoundException("该教师不存在");
        } else if (!userRepository.existsById(checks.getStudentId())) {
            throw new StudentNotFoundException("该学生不存在");
        } else if (!taskRepository.existsById(checks.getTaskId())) {
            throw new TaskNotFoundException("该实验项目不存在");
        }
        Optional<FinishesEntity> finishesOptional = finishesRepository.findById(checks.getStudentId(), checks.getTaskId());
        if (finishesOptional.isPresent()) {
            FinishesEntity finishes = finishesOptional.get();
            finishes.setScore(checks.getScore());
            finishesRepository.save(finishes);
        } else {
            FinishesEntity finishes = new FinishesEntity();
            finishes.setStudentId(checks.getStudentId());
            finishes.setTaskId(checks.getTaskId());
            finishes.setFinished(0);
            finishes.setScore(checks.getScore());
            finishesRepository.save(finishes);

            log.info("新增作业提交记录：" +
                    "学生 " + checks.getStudentId() +
                    " 没有提交，但是教师" + checks.getTeacherId() +
                    "批改了。");
        }
        checks.setCheckTime(Timestamp.valueOf(LocalDateTime.now()));
        checksRepository.save(checks);
        log.info("新增批改记录：" +
                "教师 " + checks.getTeacherId() + " 在 " +
                checks.getCheckTime() + " 时间批改了" +
                "学生 " + checks.getStudentId() +
                " 的实验项目 " + checks.getTaskId() +
                " 的成绩：" + checks.getScore());
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("message", "新增批改记录成功");
        return json;

    }
}
