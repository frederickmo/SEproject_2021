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

    public boolean checkExist(int uploadID,int courseID) {
        Optional<ManagesEntity> managesTemp = managesRepository.findById(uploadID,courseID);
        if (managesTemp.isPresent()) {
            return true;
        }
        else return  false;
    }
}
