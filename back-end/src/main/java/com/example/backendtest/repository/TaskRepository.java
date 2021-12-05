package com.example.backendtest.repository;

import com.example.backendtest.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    public Optional<List<TaskEntity>> findByCourseId(Integer courseId,Integer type);
}
