package com.example.backendtest.service;

import cn.hutool.cron.task.Task;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.AlreadyExistException;
import com.example.backendtest.exception.CourseNotFoundException;
import com.example.backendtest.exception.MyNotFoundException;
import com.example.backendtest.exception.TaskNotFoundException;
import com.example.backendtest.model.CourseEntity;
import com.example.backendtest.model.TaskEntity;
import com.example.backendtest.repository.CourseRepository;
import com.example.backendtest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    /**
     * 按实验项目ID查询
     * @param id 实验项目ID
     * @return 实验项目详细信息
     */
    public TaskEntity getByTaskId(Integer id) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException("该实验项目不存在");
        } else {
            return taskOptional.get();
        }
    }

    /**
     * 按课程ID查询其实验项目
     * @param courseId 课程ID
     * @return 该课程下所有实验项目
     */
    public List<TaskEntity> getByCourseId(Integer courseId) {
        Optional<List<TaskEntity>> tasksOptional = taskRepository.findByCourseId(courseId);
        if (tasksOptional.isEmpty()) {
            throw new MyNotFoundException("该课程下无实验项目！");
        } else {
            return tasksOptional.get();
        }
    }

    /**
     * 添加实验项目（可以不指定其所属课程）=> 似乎不能不指定，因为指定了外码约束
     * @param task 实验项目实体
     * @return 添加结果json
     */
    public JSONObject add(TaskEntity task) {
        log.info("task.id: " + task.getId());
        log.info("task.courseId: " + task.getCourseId());
        Optional<TaskEntity> taskOptional = taskRepository.findById(task.getId());
        Optional<CourseEntity> courseOptional = courseRepository.findById(task.getCourseId());
        if (taskOptional.isPresent()) {
            throw new AlreadyExistException("该实验项目已存在");
        } else if (courseOptional.isEmpty()) {
            throw new CourseNotFoundException("该实验项目所属课程不存在");
        }
        else {
            taskRepository.save(task);
            log.info("成功添加实验项目 => ID: " + task.getId());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "成功添加实验项目");
            return json;
        }
    }

    public JSONObject remove(Integer taskId) {
        boolean taskExists = taskRepository.existsById(taskId);
        if (!taskExists) {
            throw new TaskNotFoundException("该实验项目不存在");
        } else {
            taskRepository.deleteById(taskId);
            log.info("删除实验项目: ID " + taskId);
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "成功删除实验项目");
            return json;
        }
    }

    /**
     * 通过课程id找到小型项目
     * @return 返回所有找到的小型项目
     */
    public List<TaskEntity> getSimpleTask(Integer courseId) {

        Optional<List<TaskEntity>> tasksOptional = taskRepository.findByCourseIdAndType(courseId,0);
        if (tasksOptional.isEmpty()) {
            throw new MyNotFoundException("该课程下无小型实验项目");
        } else {
            return tasksOptional.get();
        }
    }

    /**
     * 通过课程id找到大型项目
     * @return 返回所有找到的大型项目
     */
    public List<TaskEntity> getComplexTask(Integer courseId) {
        Optional<List<TaskEntity>> tasksOptional = taskRepository.findByCourseIdAndType(courseId,1);
        if (tasksOptional.isEmpty()) {
            throw new MyNotFoundException("该课程下无大型实验项目");
        } else {
            return tasksOptional.get();
        }
    }

    /**
     * 按学号查询学生选课的所有未完成的实验项目的信息
     * @return (学生ID, 课程ID, 课程名, 项目ID, 项目名, 截止时间)
     */
    public List<Object> getAllByStudentIdAndUnfinishedOrderByDeadlineAsc(Integer studentId) {
        Optional<List<Object>> tasksOptional = taskRepository.findAllByCourseIdAndUnfinishedOrderByDeadlineAsc(studentId);
        if (tasksOptional.isEmpty()) {
            throw new MyNotFoundException("该学生选修的课程中无任何未完成的实验项目");
        } else {
            return tasksOptional.get();
        }
    }

    /**
     * 按学号查询学生选课的所有已完成的实验项目的信息
     * @return (学生ID, 课程ID, 课程名, 项目ID, 项目名, 截止时间)
     */
    public List<Object> getAllByStudentIdAndFinishedOrderByDeadlineAsc(Integer studentId) {
        Optional<List<Object>> tasksOptional = taskRepository.findAllByCourseIdAndFinishedOrderByDeadlineAsc(studentId);
        if (tasksOptional.isEmpty()) {
            throw new MyNotFoundException("该学生选修的课程中无任何已完成的实验项目");
        } else {
            return tasksOptional.get();
        }
    }

    public JSONObject updateTaskInformation(TaskEntity taskEntity) {
        if(taskRepository.findById(taskEntity.getId()).isEmpty())
        {
            throw  new TaskNotFoundException("不存在该实验项目");
        }
        else
        {
            taskRepository.updateTaskInformation(taskEntity.getId(),taskEntity.getCourseId(),taskEntity.getName()
                    ,taskEntity.getDescription(),taskEntity.getDeadline(),taskEntity.getType(),taskEntity.getUrl());
            JSONObject json = new JSONObject();
            json.put("code", 200);
            json.put("message", "项目信息修改成功");
            return json;
        }
    }
}
