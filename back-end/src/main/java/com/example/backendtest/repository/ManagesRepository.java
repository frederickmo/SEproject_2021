package com.example.backendtest.repository;

import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.ManagesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagesRepository extends JpaRepository<ManagesEntity, ManagesEntityPK> {

    @Query(value = "select t from ManagesEntity  t where t.teacherId = ?1 and t.courseId = ?2")
    public Optional<ManagesEntity> findById(Integer uploadID, Integer courseID);
}
