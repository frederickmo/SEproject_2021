package com.example.backendtest.repository;

import com.example.backendtest.model.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Integer> {

    @Query(value = "select s from NoticeEntity s where s.topic = ?1")
    public Optional<NoticeEntity> findByTopic(String topic);


    @Query(value = "select s from NoticeEntity s order by s.updatedTime desc")
    List<NoticeEntity> showNoticeByTime();

    @Query(value = "select n from NoticeEntity n where n.type = 0 or n.type = 1 order by n.updatedTime desc")
    List<NoticeEntity> findAllForStudent();

    @Query(value = "select n from NoticeEntity n where n.type = 0 or n.type = 2 order by n.updatedTime desc")
    List<NoticeEntity> findAllForTeachers();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  NoticeEntity s set s.postedId = ?2,s.topic = ?3,s.content = ?4,s.type=?5,s.updatedTime =?6  " +
            "where s.id = ?1")
    void updateNotice(int id, int postedId, String topic, String content, int type, Date updatedTime);
}
