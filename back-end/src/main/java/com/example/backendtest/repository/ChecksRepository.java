package com.example.backendtest.repository;

import com.example.backendtest.model.ChecksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChecksRepository extends JpaRepository<ChecksEntity, Integer> {

    @Query(value = "select c from ChecksEntity c where c.teacherId = ?1")
    public Optional<List<ChecksEntity>> findAllByTeacherId(Integer teacherId);

    @Query(value = "select c from ChecksEntity c where c.taskId = ?1")
    public Optional<List<ChecksEntity>> findAllByTaskId(Integer taskId);

    @Query(value = "select c from ChecksEntity c where c.teacherId = ?1 and c.taskId = ?2")
    public Optional<List<ChecksEntity>> findAllByTeacherIdAndTaskId(Integer teacherId, Integer taskId);

    @Query(value = "select c from ChecksEntity c where c.studentId = ?1 and c.taskId = ?2")
    public Optional<List<ChecksEntity>> findAllByStudentIdAndTaskId(Integer studentId, Integer taskId);
}
