package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.repository.ManagesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
/**
 * 检查上传者是否为任课的老师
 */
public class ManagesService {

    private ManagesRepository managesRepository;

    public List<ManagesEntity> getAllByTeacherId(Integer teacherId) {
        Optional<List<ManagesEntity>> managesOptional = managesRepository.findAllByTeacherId(teacherId);
        if(managesOptional.isEmpty()) {
            throw new IllegalStateException("该教师无管理课程");
        } else {
            return managesOptional.get();
        }
    }

    public List<ManagesEntity> getAllByCourseId(Integer courseId) {
        Optional<List<ManagesEntity>> managesOptional = managesRepository.findAllByCourseId(courseId);
        if (managesOptional.isEmpty()) {
            throw new IllegalStateException("该课程无管理教师");
        } else {
            return managesOptional.get();
        }
    }

    public List<CourseEntity> getAllByTeacherIdInDetail(Integer teacherId) {
        Optional<List<CourseEntity>> coursesOptional = managesRepository.findAllByTeacherIdInDetail(teacherId);
        if (coursesOptional.isEmpty()) {
            throw new IllegalStateException("该教师无管理课程");
        } else {
            return coursesOptional.get();
        }
    }

    public List<UserEntity> getAllByCourseIdInDetail(Integer courseId) {
        Optional<List<UserEntity>> teachersOptional = managesRepository.findAllByCourseIdInDetail(courseId);
        if (teachersOptional.isEmpty()) {
            //参照外码约束，以下情况一般来说不会发生
            throw new IllegalStateException("该课程无管理教师");
        } else {
            return teachersOptional.get();
        }
    }

    public JSONObject checkExist(int uploadID,int courseID) {
        Optional<ManagesEntity> managesTemp = managesRepository.findById(uploadID,courseID);
        if (managesTemp.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "教师授课关系已存在");
            json.put("boolean",true);
            return json;
        }
        JSONObject json = new JSONObject();
        json.put("status", 404);
        json.put("message", "教师授课关系不存在");
        json.put("boolean",false);
        return json;
    }

    public JSONObject addTeacherManages(Integer teacherId, Integer courseId) {
        Optional<ManagesEntity> managesTemp = managesRepository.findById(teacherId,courseId);
        if (managesTemp.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "教师授课关系已存在");
            return json;
    }
        else
        {
            Optional<UserEntity> userTemp = managesRepository.checkTeacherId(teacherId);
             if(userTemp.isEmpty())
             {
                 throw new IllegalStateException("该人不是教师/助教");
             }
             else
             {
                 ManagesEntity managesEntity = new ManagesEntity();
                 managesEntity.setTeacherId(teacherId);managesEntity.setCourseId(courseId);
                 managesRepository.save(managesEntity);
                 log.info("新增课程关系 => 课程ID: " + courseId + " 教师/助教ID: " + teacherId);
                 JSONObject json = new JSONObject();
                 json.put("status", 200);
                 json.put("message", "课程关系添加成功");
                 return json;
             }

        }
    }

    public JSONObject deleteTeacherManages(Integer teacherId, Integer courseId) {
        Optional<ManagesEntity> managesTemp = managesRepository.findById(teacherId,courseId);
        if (managesTemp.isEmpty()) {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "该授课关系不存在");
            return json;
        }
        else
        {
            Optional<UserEntity> userTemp = managesRepository.checkTeacherId(teacherId);
            if(userTemp.isEmpty())
            {
                throw new IllegalStateException("该人不是教师/助教");
            }
            else
            {
                ManagesEntity managesEntity = new ManagesEntity();
                managesEntity.setTeacherId(teacherId);managesEntity.setCourseId(courseId);
                managesRepository.delete(managesEntity);
                log.info("删除课程关系 => 课程ID: " + courseId + " 教师/助教ID: " + teacherId);
                JSONObject json = new JSONObject();
                json.put("status", 200);
                json.put("message", "课程关系删除成功");
                return json;
            }
        }

    }

    public List<ManagesEntity> showAllIdByCourseId(Integer courseId) {
        List<ManagesEntity> list = managesRepository.findAllById(courseId);
        if(list.isEmpty())
        {
            throw new IllegalStateException("该课程不存在在执教关系");
        }
        else
        {
            return list;
        }
    }
}
