package com.example.backendtest.repository;

import com.example.backendtest.model.ScoreEntity;
import com.example.backendtest.model.ScoreEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<ScoreEntity, ScoreEntityPK> {

    @Query(value = "select s from ScoreEntity s where s.studentId = ?1")
    List<ScoreEntity> findAllByStudentId(Integer studentId);

    @Query(value = "select s from ScoreEntity s where s.courseId = ?1")
    List<ScoreEntity> findAllByCourseId(Integer courseId);

    @Query(value = "select s from ScoreEntity s where s.studentId = ?1 and s.courseId = ?2")
    Optional<ScoreEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
