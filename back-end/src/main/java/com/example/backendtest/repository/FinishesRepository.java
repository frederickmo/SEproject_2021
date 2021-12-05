package com.example.backendtest.repository;

import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.FinishesEntityPK;
import com.example.backendtest.model.ManagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FinishesRepository extends JpaRepository<FinishesEntity, FinishesEntityPK> {
    @Query(value = "select t from FinishesEntity  t where t.studentId = ?1 and t.taskId = ?2")
    public Optional<FinishesEntity> findById(Integer studentId, Integer taskId);

    @Query(value="select a.studentId,a.courseId,b.id from TakesEntity a ,TaskEntity b where a.courseId = b.courseId and b.id= ?2 and a.studentId = ?1")
    public List<Object> getStudentCourseTaskList(Integer studentId,Integer taskId);

    @Query(value = "select f from FinishesEntity f where f.studentId = ?1")
    public Optional<List<FinishesEntity>> findAllByStudentId(Integer studentId);

}
