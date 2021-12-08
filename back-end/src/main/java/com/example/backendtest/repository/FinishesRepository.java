package com.example.backendtest.repository;

import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.FinishesEntityPK;
import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FinishesRepository extends JpaRepository<FinishesEntity, FinishesEntityPK> {
    @Query(value = "select t from FinishesEntity  t where t.studentId = ?1 and t.taskId = ?2")
    public Optional<FinishesEntity> findById(Integer studentId, Integer taskId);

    @Query(value="select a.studentId,a.courseId,b.id from TakesEntity a ,TaskEntity b " +
            "where a.courseId = b.courseId and b.id= ?2 and a.studentId = ?1")
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


//    @Query(value = "select t.taskId, t.studentId, u.name, t.answer, t.finished, t.score " +
//            "from FinishesEntity t, UserEntity u " +
//            "where t.taskId = ?1 and t.studentId = u.id and t.finished = 1 ")
//    public Optional<Object> findAllFinishedRecordsByTaskId(Integer taskId);

    /**
     * 获取finishes表里某一个taskId的已提交的学生的数据
     */
    @Query(value = "select f.taskId, f.studentId, u.name, f.answer, f.finished, f.score " +
            "from FinishesEntity f, UserEntity u " +
            "where f.taskId = ?1 and f.studentId = u.id and f.finished = 1 ")
    public Optional<List<Object>> findAllSubmitRecordsByTaskId(Integer taskId);

    /**
     * 未提交获取方式①：
     * 获取finishes表里某一个taskId的未提交的学生的数据
     */
    @Query(value = "select f.taskId, f.studentId, u.name, f.finished, f.score " +
            "from FinishesEntity f, UserEntity u " +
            "where f.taskId = ?1 and f.studentId = u.id and f.finished = 0 ")
    public Optional<List<Object>> findAllUnfinishedRecordsByTaskId(Integer taskId);

    /**
     * 未提交获取方式②：
     * 获取未提交且未被给分过的学生名单： => 干脆直接获取UserEntity吧
     * 从takes里选了本门课的学生里查
     */
    @Query(value = "select distinct u " +
            "from TakesEntity t, UserEntity u, FinishesEntity f, TaskEntity t2 " +
            "where t.courseId = t2.courseId and t2.id = ?1 " +
            "and t2.id = f.taskId and u.id = t.studentId " +
            "and t.studentId not in " +
            "(select f2.studentId from FinishesEntity f2 where f2.taskId = ?1)")
    public Optional<List<UserEntity>> findAllUnfinishedStudentsByTaskId(Integer taskId);
}
