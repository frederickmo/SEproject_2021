package com.example.backendtest.repository;

import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.backendtest.model.TakesEntity;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * TaskId的命名规范：
 * 前三位 为课程ID
 * 第四位 代表是否为大型实验项目或小型试验项目（大型实验项目该位为1，小型为0）
 * 后两位 为在当前课程下的实验序号（大型/小型分开计算）
 *      => 即：102001和102101可以共存。
 * 例：主机路由实验 => 隶属于《计算机网络实验》（课程ID102）之下，是小型实验项目，在小型实验项目里编号为05
 * 故主机路由实验的taskId为102005
 */


public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     * 经过测试，查询结果为多个元组的时候，返回值确实是List
     */
    @Query(value = "select t from TaskEntity t where t.courseId = ?1")
    public Optional<List<TaskEntity>> findByCourseId(Integer courseId);


    @Query(value = "select t from TaskEntity t where t.courseId = ?1 and t.type = ?2")
    public Optional<List<TaskEntity>> findByCourseIdAndType(Integer courseId,Integer type);

    /**
     * 试试用Query语句写一下join查询
     * 按学生ID查询其已经完成的实验项目的信息(按截止日期升序排列，截止日期位置的排队尾)
     * 查询结果总共包括6个属性: (studentId, courseId, courseName, taskId, taskName, deadline, type)
     * 从四个表(takes/task/course)里查
     * 其中(studentId, taskId)的组合在 (finishes表中finished属性为1的元组) 中不存在 => 即该学生未完成该ID的作业
     * asc => 按截止日期升序排列，即ddl越靠前排越前
     * nulls last => ddl为空排最后
     */

    @Query(value = "select t2.studentId, t2.courseId, c.name as courseName, " +
            "t1.id as taskId, t1.name as taskName, t1.deadline, t1.type " +
            "from TaskEntity t1, TakesEntity t2, CourseEntity c " +
            "where t1.courseId = t2.courseId and t1.courseId = c.id " +
            "and t2.studentId = ?1 " +
            "and not exists " +
            "(select f from FinishesEntity f where f.finished = 1 and f.taskId = t1.id and f.studentId = t2.studentId) " +
            "order by t1.deadline asc nulls last")
    public Optional<List<Object>> findAllByCourseIdAndUnfinishedOrderByDeadlineAsc(Integer studentId);

    /**
     * 和上面相反，按学生ID查询已经完成的实验项目的信息(按截止日期升序排列，截止日期位置的排队尾)
     */
    @Query(value = "select t2.studentId, t2.courseId, c.name as courseName, " +
            "t1.id as taskId, t1.name as taskName, t1.deadline, t1.type " +
            "from TaskEntity t1, TakesEntity t2, CourseEntity c, FinishesEntity f " +
            "where t1.courseId = t2.courseId and t1.courseId = c.id " +
            "and t2.studentId = ?1 " +
            "and t2.studentId = f.studentId and t1.id = f.taskId " +
            "and f.finished = 1 " +
            "order by t1.deadline asc nulls last")
    public Optional<List<Object>> findAllByCourseIdAndFinishedOrderByDeadlineAsc(Integer studentId);

    /**
     * 修改task的详细信息
     * @param id
     * @param courseId
     * @param name
     * @param description
     * @param deadline
     * @param type
     * @param url
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update TaskEntity s set s.courseId = ?2,s.name=?3,s.description=?4,s.deadline =?5,s.type=?6 ,s.url=?7 where s.id = ?1")
    void updateTaskInformation(int id, Integer courseId, String name, String description, Date deadline, Integer type, String url);

}
