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

    @Query(value = "select t.courseId, c.name as courseName, f.taskId, t.name as taskName, f.score, f.answer " +
            "from FinishesEntity f, TaskEntity t, CourseEntity c " +
            "where f.taskId = t.id and t.courseId = c.id and f.studentId = ?1 ")
    public Optional<List<Object>> findAllByStudentIdInDetail(Integer studentId);


    /**
     * 以下这个查询并不直接提供给Controller，而是在service里经过按课程ID分别查找每门课的scoreList
     * 最后返回的是一个List<List<Object>> => 三维数组
     */
    @Query(value = "select t.courseId, c.name as courseName, f.taskId, t.name as taskName, f.score, f.answer " +
            "from FinishesEntity f, TaskEntity t, CourseEntity c " +
            "where f.taskId = t.id and t.courseId = c.id and f.studentId = ?1 and c.id = ?2 ")
    public Optional<List<Object>> findAllByStudentIdAndCourseIdInDetail(Integer studentId, Integer courseId);

}
