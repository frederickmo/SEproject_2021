package com.example.backendtest.service;


import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.repository.FinishesRepository;
import com.example.backendtest.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

/**
 * 用于处理提交作业的服务
 */
public class FinishesService {

   private final FinishesRepository finishesRepository;
   private final FileStorageService fileStorageService;


    /**
     * 上传作业之前先查询一下是否已经上传
     */
   public boolean isTaskFinished(Integer studentId, Integer taskId) {
       Optional<FinishesEntity> finishesOptional = finishesRepository.findById(studentId, taskId);
       if (finishesOptional.isPresent()) {
           return true;
       } else {
           return false;
       }
   }

    public JSONObject submitOnlineTask(JSONObject taskBody) {

        /**
         * 以下信息是不存在于实验报告中的，但是由于前端传数据没法既传参数又传requestBody，
         * 所以只能把参数一起写在requestBody中了
         */
       Integer studentId = taskBody.getInteger("studentId");
       Integer courseId = taskBody.getInteger("courseId");
       Integer taskId = taskBody.getInteger("taskId");

       if (studentId == null || courseId == null || taskId == null) {
           throw new IllegalStateException("读取上传信息失败");
       }

        String body = taskBody.toJSONString();
        BufferedWriter writer = null;
        String location = fileStorageService.getFileStorageLocation() + "/taskUpload/" + courseId + "/" + taskId;

        File file = new File(location + "/" + taskId + "_" +  studentId + ".json");

        /**
         * 若需要上传的实验文件夹不存在则创建
         */
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        /**
         * 文件不存在则创建，文件存在时则进行覆盖（符合用户逻辑的重新上传）
         */
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
            writer.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FinishesEntity finishes = new FinishesEntity();
        finishes.setTaskId(taskId);
        finishes.setStudentId(studentId);
        finishes.setFinishTime(Timestamp.valueOf(LocalDateTime.now()));
        finishes.setFinished(1);

        // TODO: 后来想到上传的本质还是存到本地，而且后端本地无法读取到完整的下载路径返回给前端，
        // => 举例： http://localhost:8081/file/download 这个路径前面的localhost后端是无法获取到的，
        // 所以这个返回一个url的属性是无法存到数据库里的，用户需要的话自己用下载文件的接口去文件夹里找算了。
        finishes.setAnswer("true");

        /**
         * 下面的语句如果在用户填入的ID不存在时会报SQLIntegrityConstraintViolationException，但是暂时不知道咋解决
         */
        finishesRepository.save(finishes);

        log.info("新增学生提交实验报告信息: 学生ID " + studentId + " 实验ID " + taskId);

        JSONObject json = new JSONObject();
        json.put("status", 200);
        json.put("message", "上传成功");
        return json;
    }

    public JSONObject submitComplexTask(MultipartFile file,
                                        Integer studentId,
                                        Integer taskId,
                                        String newFileName,
                                        String location) {
       log.info("获取到的studentId: " + studentId + " taskId: " + taskId);
       fileStorageService.storeAndRename(file, newFileName, location);
       FinishesEntity finishes = new FinishesEntity();
       finishes.setTaskId(taskId);
       finishes.setStudentId(studentId);
       finishes.setFinishTime(Timestamp.valueOf(LocalDateTime.now()));
       finishes.setFinished(1);
       finishes.setAnswer("true");
       finishesRepository.save(finishes);

       JSONObject json = new JSONObject();
       json.put("status", 200);
       json.put("message", "提交成功！");
       return json;
    }

    public List<FinishesEntity> getAllSubmitRecords(Integer studentId) {
        Optional<List<FinishesEntity>> submitRecords = finishesRepository.findAllByStudentId(studentId);
        if (submitRecords.isEmpty()) {
            throw new IllegalStateException("该学生无提交记录");
        } else {
            return submitRecords.get();
        }
    }

    public List<Object> getAllScoresOfSubmitRecordsInDetail(Integer studentId) {
        Optional<List<Object>> recordsInDetailOptional = finishesRepository.findAllByStudentIdInDetail(studentId);
        if (recordsInDetailOptional.isEmpty()) {
            throw new IllegalStateException("该学生无实验项目提交记录");
        } else {
            return recordsInDetailOptional.get();
        }
    }





    /**
     * 这个接口有两个问题：
     * ①用户不能主动上传url，即使能，从业务逻辑的角度，也不应该让用户上传一个url
     * ②关于是否已经提交，应该先查询是否已经提交再进行提交，不能提交之后再告诉你已经提交过了
     * => 所以我在上面另外写了一个查询是否已经提交的接口
     */
    public JSONObject uploadHomework(Integer studentId, Integer taskId,String url) {
        Optional<FinishesEntity> finishesEntity = finishesRepository.findById(studentId,taskId);
        if(finishesEntity.isPresent())
        {
            JSONObject json = new JSONObject();
            json.put("status", 001);
            json.put("message", "该作业已提交，是否考虑重新提交");
            json.put("boolean",false);
            return json;
        }
            else
        {
            FinishesEntity finishesEntity2 = new FinishesEntity();
            finishesEntity2.setFinished(1);
            finishesEntity2.setStudentId(studentId);
            finishesEntity2.setTaskId(taskId);
            finishesEntity2.setAnswer(url);
            finishesEntity2.setScore(null);
            TimeUtil timeUtil =new TimeUtil();
            finishesEntity2.setFinishTime(timeUtil.getTimestampNow());
            finishesRepository.save(finishesEntity2);
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "成功提交");
            json.put("boolean",true);
            return json;
        }
    }

    public JSONObject checkStudentForTask(Integer studentId, Integer taskId) {
        List<Object> list = finishesRepository.getStudentCourseTaskList(studentId,taskId);
       if(list.isEmpty())
        {
            JSONObject json = new JSONObject();
            json.put("status", 000);
            json.put("message", "学生，课程，任务关系不匹配");
            json.put("boolean",false);
            return json;
        }
        else
        {
            JSONObject json = new JSONObject();
            json.put("status", 001);
            json.put("message", "学生，课程，任务关系匹配");
            json.put("boolean",true);
            return json;
        }

    }
}
