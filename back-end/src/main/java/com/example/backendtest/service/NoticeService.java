package com.example.backendtest.service;


import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.NoticeEntity;
import com.example.backendtest.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class NoticeService {

private NoticeRepository noticeRepository;
    public JSONObject addNotice(int postedId, String topic, String content, int type, Date updatedTime) {
        if(noticeRepository.findByTopic(topic).isPresent())
        {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "存在同名公告");
            return json;
        }
        else {
            NoticeEntity noticeEntity = new NoticeEntity();
            noticeEntity.setPostedId(postedId);
            noticeEntity.setTopic(topic);
            noticeEntity.setContent(content);
            noticeEntity.setType(type);
            noticeEntity.setUpdatedTime(updatedTime);
            noticeRepository.save(noticeEntity);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "已添加存在");
            return json;
        }
    }

    public List<NoticeEntity> showNoticeByTime() {
        if (noticeRepository.findAll().isEmpty()){throw  new IllegalStateException("没有公告");}
        return noticeRepository.showNoticeByTime();
    }

    public JSONObject deleteNotice(String topic) {
        if(noticeRepository.findByTopic(topic).isEmpty())
        {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "不存在该公告");
            return json;
        }
        else
        {
            noticeRepository.deleteByTopic(topic);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "公告删除成功");
            return json;
        }
    }

    public JSONObject updateNotice(int id, int postedId, String topic, String content, int type, Date updatedTime) {
        if(noticeRepository.findById(id).isEmpty())
        {
            throw  new IllegalStateException("公告编号不存在");
        }
        else
        {
            noticeRepository.updateNotice(id,postedId,topic,content,type,updatedTime);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "公告修改成功");
            return json;
        }
    }
}
