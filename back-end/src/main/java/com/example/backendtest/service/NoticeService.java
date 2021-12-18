package com.example.backendtest.service;


import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.AlreadyExistException;
import com.example.backendtest.exception.MyNotFoundException;
import com.example.backendtest.model.NoticeEntity;
import com.example.backendtest.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public JSONObject addNotice(NoticeEntity notice) {
        if(noticeRepository.findByTopic(notice.getTopic()).isPresent())
        {
            throw new AlreadyExistException("同名公告已存在");
        }
        else {
            notice.setUpdatedTime(Date.valueOf(LocalDate.now()));
            noticeRepository.save(notice);
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "添加成功");
            return json;
        }
    }

    public List<NoticeEntity> showNoticeByTimeDesc() {
        if (noticeRepository.findAll().isEmpty())
        {
            throw new MyNotFoundException("无任何公告");
        }
        return noticeRepository.showNoticeByTime();
    }

    public JSONObject deleteNotice(int id) {
        if(noticeRepository.findById(id).isEmpty())
        {
            throw new MyNotFoundException("该公告不存在");
        }
        else
        {
            noticeRepository.deleteById(id);
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "公告删除成功");
            return json;
        }
    }

    public JSONObject updateNotice(NoticeEntity notice) {
        if(noticeRepository.findById(notice.getId()).isEmpty())
        {
            throw new MyNotFoundException("该公告不存在");
        }
        else
        {
            noticeRepository.updateNotice(notice.getId(),notice.getPostedId(),notice.getTopic(),notice.getContent(),notice.getType(),notice.getUpdatedTime());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "公告修改成功");
            return json;
        }
    }
}
