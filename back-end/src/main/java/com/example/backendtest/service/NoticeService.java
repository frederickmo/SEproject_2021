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

    private final NoticeRepository noticeRepository;

    public JSONObject addNotice(NoticeEntity notice) {
        if(noticeRepository.findByTopic(notice.getTopic()).isPresent())
        {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "存在同名公告");
            return json;
        }
        else {
            noticeRepository.save(notice);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "已添加存在");
            return json;
        }
    }

    public List<NoticeEntity> showNoticeByTime() {
        if (noticeRepository.findAll().isEmpty())
        {
            throw  new IllegalStateException("没有公告");
        }
        return noticeRepository.showNoticeByTime();
    }

    public JSONObject deleteNotice(int id) {
        if(noticeRepository.findById(id).isEmpty())
        {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "不存在该公告");
            return json;
        }
        else
        {
            noticeRepository.deleteById(id);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "公告删除成功");
            return json;
        }
    }

    public JSONObject updateNotice(NoticeEntity notice) {
        if(noticeRepository.findById(notice.getId()).isEmpty())
        {
            JSONObject json = new JSONObject();
            json.put("status", 500);
            json.put("message", "不存在该该公告");
            return json;
        }
        else
        {
            noticeRepository.updateNotice(notice.getId(),notice.getPostedId(),notice.getTopic(),notice.getContent(),notice.getType(),notice.getUpdatedTime());
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "公告修改成功");
            return json;
        }
    }
}
