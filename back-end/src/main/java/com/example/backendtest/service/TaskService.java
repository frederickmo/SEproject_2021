package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
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
            throw new IllegalStateException("该实验项目不存在！");
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
            throw new IllegalStateException("该课程下无实验项目！");
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
            throw new IllegalStateException("该实验项目已存在！");
        } else if (courseOptional.isEmpty()) {
            throw new IllegalStateException("该实验项目所属课程不存在！");
        }
        else {
            taskRepository.save(task);
            log.info("成功添加实验项目 => ID: " + task.getId());
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "成功添加实验项目");
            return json;
        }
    }

    public JSONObject remove(Integer taskId) {
        boolean taskExists = taskRepository.existsById(taskId);
        if (!taskExists) {
            throw new IllegalStateException("该实验项目不存在");
        } else {
            taskRepository.deleteById(taskId);
            log.info("删除实验项目: ID " + taskId);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "成功删除实验项目");
            return json;
        }
    }

    /**
     * 通过课程id找到小型项目
     * @param courseId
     * @return 返回所有找到的小型项目
     */
    public List<TaskEntity> getSmallCourse(Integer courseId) {

        Optional<List<TaskEntity>> tasksOptional = taskRepository.findByCourseId(courseId,1);
        if (tasksOptional.isPresent()) {
            throw new IllegalStateException("该课程下无小型实验项目！");
        } else {
            return tasksOptional.get();
        }
    }

    /**
     * 通过课程id找到大型项目
     * @param courseId
     * @return 返回所有找到的大型项目
     */
    public List<TaskEntity> getBigCourse(Integer courseId) {
        Optional<List<TaskEntity>> tasksOptional = taskRepository.findByCourseId(courseId,0);
        if (tasksOptional.isEmpty()) {
            throw new IllegalStateException("该课程下无大型实验项目！");
        } else {
            return tasksOptional.get();
        }
    }
}
