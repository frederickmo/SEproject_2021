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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from NoticeEntity s where s.topic = ?1")
    void deleteByTopic(String topic);

    @Query(value = "select s from NoticeEntity s order by s.updatedTime desc")
    List<NoticeEntity> showNoticeByTime();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update  NoticeEntity s set s.postedId = ?2,s.topic = ?3,s.content = ?4,s.type=?5,s.updatedTime =?6  " +
            "where s.id = ?1")
    void updateNotice(int id, int postedId, String topic, String content, int type, Date updatedTime);
}
