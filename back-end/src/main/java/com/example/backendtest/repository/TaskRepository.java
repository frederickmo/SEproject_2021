package com.example.backendtest.repository;

import com.example.backendtest.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     * 经过测试，查询结果为多个元组的时候，返回值确实是List
     */
    @Query(value = "select t from TaskEntity t where t.courseId = ?1")
    public Optional<List<TaskEntity>> findByCourseId(Integer courseId);


    @Query(value = "select t from TaskEntity t where t.courseId = ?1 and t.type = ?2")
    public Optional<List<TaskEntity>> findByCourseId(Integer courseId,Integer type);
}
