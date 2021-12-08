package com.example.backendtest.repository;

import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.ManagesEntityPK;
import com.example.backendtest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManagesRepository extends JpaRepository<ManagesEntity, ManagesEntityPK> {

    @Query(value = "select t from ManagesEntity  t where t.teacherId = ?1 and t.courseId = ?2")
    public Optional<ManagesEntity> findById(Integer uploadID, Integer courseID);

    @Query(value = "select s from UserEntity s where s.id=?1 and (s.identity = 3 or s.identity = 4 or s.identity = 2 )")
    Optional<UserEntity> checkTeacherId(Integer teacherId);

    @Query(value = "select s from ManagesEntity s where s.courseId = ?1 ")
    List<ManagesEntity> findAllById(Integer courseId);
}
