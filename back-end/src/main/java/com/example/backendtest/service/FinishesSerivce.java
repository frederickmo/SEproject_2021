package com.example.backendtest.service;


import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.repository.FinishesRepository;
import com.example.backendtest.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

/**
 * 用于处理提交作业的服务
 */
public class FinishesSerivce {

   private final FinishesRepository finishesRepository;
    public JSONObject uploadHomework(Integer studentId, Integer taskId,String url) {
        Optional<FinishesEntity> finishesEntity = finishesRepository.findById(studentId,taskId);
        if(finishesEntity.isPresent())
        {
            JSONObject json = new JSONObject();
            json.put("status", 001);
            json.put("message", "该作业已提交，是否考虑重新提交");
            json.put("boolean",false);
            return json;
        }
            else
        {
            FinishesEntity finishesEntity2 = new FinishesEntity();
            finishesEntity2.setFinished(1);
            finishesEntity2.setStudentId(studentId);
            finishesEntity2.setTaskId(taskId);
            finishesEntity2.setAnswer(url);
            finishesEntity2.setScore(null);
            TimeUtil timeUtil =new TimeUtil();
            finishesEntity2.setFinishTime(timeUtil.getTimestampNow());
            finishesRepository.save(finishesEntity2);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "成功提交");
            json.put("boolean",true);
            return json;
        }
    }

    public JSONObject checkStudentForTask(Integer studentId, Integer taskId) {
        List<Object> list = finishesRepository.getStudentCourseTaskList(studentId,taskId);
       if(list.isEmpty())
        {
            JSONObject json = new JSONObject();
            json.put("status", 000);
            json.put("message", "学生，课程，任务关系不匹配");
            json.put("boolean",false);
            return json;
        }
        else
        {
            JSONObject json = new JSONObject();
            json.put("status", 001);
            json.put("message", "学生，课程，任务关系匹配");
            json.put("boolean",true);
            return json;
        }

    }
}
