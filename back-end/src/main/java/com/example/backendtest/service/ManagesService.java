package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.repository.ManagesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



    public JSONObject checkExist(int uploadID,int courseID) {
        Optional<ManagesEntity> managesTemp = managesRepository.findById(uploadID,courseID);
        if (managesTemp.isPresent()) {
            JSONObject json = new JSONObject();
            json.put("status", 000);
            json.put("message", "教师授课关系已存在");
            json.put("boolean",true);
            return json;
        }
        JSONObject json = new JSONObject();
        json.put("status", 001);
        json.put("message", "教师授课关系不存在");
        json.put("boolean",false);
        return json;
    }
}
