package com.example.backendtest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.FailInReadingSubmitInfoException;
import com.example.backendtest.exception.FileNameIllegalException;
import com.example.backendtest.exception.MyNotFoundException;
import com.example.backendtest.exception.UserNotFoundException;
import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.UserEntity;
import com.example.backendtest.repository.FinishesRepository;
import com.example.backendtest.repository.UserRepository;
import com.example.backendtest.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

/**
 * 用于处理提交作业的服务
 */
public class FinishesService {

   private final FinishesRepository finishesRepository;
   private final UserRepository userRepository;
   private final FileStorageService fileStorageService;
   private final TakesService takesService;


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

   public FinishesEntity getByStudentIdAndTaskId(Integer studentId, Integer taskId) {
       Optional<FinishesEntity> finishesOptional = finishesRepository.findById(studentId, taskId);
       if (finishesOptional.isEmpty()) {
           throw new MyNotFoundException("该学生无提交该作业的记录");
       } else {
           return finishesOptional.get();
       }
   }

   public JSONObject getOnlineTaskJsonFile(Integer studentId, Integer taskId) {
       Optional<FinishesEntity> finishesOptional = finishesRepository.findById(studentId, taskId);
       if (finishesOptional.isEmpty()) {
           throw new MyNotFoundException("该学生无提交该作业的记录");
       } else if (finishesOptional.get().getAnswer() == null) {
           throw new FileNameIllegalException("无法读取提交的作业名");
       } else {
           String fileName = finishesOptional.get().getAnswer();
           String jsonStr = "";
           try {
               File jsonFile = new File(fileStorageService.getFileStorageLocation() + "/taskUpload/"
                       + taskId + "/" + studentId + "/" + fileName);
//               log.info("jsonFile: " + jsonFile.toString());
               FileReader fileReader = new FileReader(jsonFile);
               Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
               int ch = 0;
               StringBuffer sb = new StringBuffer();
               while((ch = reader.read()) != -1) {
                   sb.append((char) ch);
               }
               fileReader.close();
               reader.close();
               jsonStr = sb.toString();
//               log.info("jsonStr:" + jsonStr);
           } catch (IOException e) {
               e.printStackTrace();
           }

           return JSON.parseObject(jsonStr);
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
           throw new FailInReadingSubmitInfoException("读取上传信息失败");
       }

        String body = taskBody.toJSONString();
        BufferedWriter writer = null;
        // NOTICE: 此处虽然本身文件就是由后端创建的，但是为了和大型项目统一所以也放在单独的文件夹了。
        // 但方便之处是可以自行覆盖上次提交的作业。
        String location = fileStorageService.getFileStorageLocation()
                + "/taskUpload/" + taskId + "/" + studentId;

        String fileName = taskId + "_" + studentId + ".json";

        File file = new File(location + "/" + fileName);

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
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file, false),
                            StandardCharsets.UTF_8));
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

        // fileName: "{taskId}_{studentId}.json"
        finishes.setAnswer(fileName);
        finishes.setFinished(1);



        /**
         * TODO: 下面的语句如果在用户填入的ID不存在时会报SQLIntegrityConstraintViolationException，但是暂时不知道咋解决
         *
         * ↓
         * 查询一下ID是否存在似乎就解决了。
         */

        if (!userRepository.existsById(studentId)) {
            throw new UserNotFoundException("提交的学生ID不存在");
        } else {

            finishesRepository.save(finishes);

            log.info("新增学生提交实验报告信息: 学生ID " + studentId + " 实验ID " + taskId);

            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("message", "上传成功");
            return json;
        }

    }

    public JSONObject submitComplexTask(MultipartFile file,
                                        Integer studentId,
                                        Integer taskId,
                                        String location) {
       log.info("获取到的studentId: " + studentId + " taskId: " + taskId);
       String fileName = fileStorageService.storeToSpecifiedDirectory(file, location);
       FinishesEntity finishes = new FinishesEntity();
       finishes.setTaskId(taskId);
       finishes.setStudentId(studentId);
       finishes.setFinishTime(Timestamp.valueOf(LocalDateTime.now()));
       finishes.setFinished(1);
       finishes.setAnswer(fileName);
       finishesRepository.save(finishes);

       JSONObject json = new JSONObject();
       json.put("status", 200);
       json.put("message", "提交成功！");
       return json;
    }


    /**
     * 获取学生的所有课程所有项目的提交记录
     */
    public List<FinishesEntity> getAllSubmitRecords(Integer studentId) {
        Optional<List<FinishesEntity>> submitRecords = finishesRepository.findAllByStudentId(studentId);
        if (submitRecords.isEmpty()) {
            throw new MyNotFoundException("该学生无提交记录");
        } else {
            return submitRecords.get();
        }
    }

    /**
     * 这个接口不是现在需要的，但是暂时保留了
     */
    public List<Object> getAllScoresOfSubmitRecordsByStudentIdInDetail(Integer studentId) {
        Optional<List<Object>> recordsOptional = finishesRepository.findAllByStudentIdInDetail(studentId);
        if (recordsOptional.isEmpty()) {
            throw new MyNotFoundException("该学生无实验项目提交记录");
        } else {
            return recordsOptional.get();
        }
    }

    /**
     * 这个接口不是现在需要的，但是暂时保留了
     */
    public List<Object> getAllScoresOfSubmitRecordsByStudentIdAndCourseIdInDetail(Integer studentId, Integer courseId) {
        Optional<List<Object>> recordsOptional = finishesRepository.findAllByStudentIdAndCourseIdInDetail(studentId, courseId);
        if (recordsOptional.isEmpty()) {
            throw new MyNotFoundException("该学生该门课程无成绩记录");
        } else {
            return recordsOptional.get();
        }
    }

    /**
     * 调用Repository里的按学生ID和课程ID查找该课程的所有实验项目的提交记录的方法，
     * 将结果按照课程ID进行分组返回得分情况。
     * 由于每次查询某门课程的得分情况的返回值是一个List<Object>(Object内部也是一个List)，
     * 因此最终的返回结果是一个List<List<Object>(三维数组)。
     */
    public List<List<Object>> getAllScoresOfSubmitRecordsByStudentIdGroupByCourseIdInDetail(Integer studentId) {
        // 先得到该学生所有选课
        List<TakesEntity> courses = takesService.getAllCoursesByStudentId(studentId);
        List<List<Object>> scoresOfSubmitRecordsGroupByCourseId = new ArrayList<>();
        for (TakesEntity course : courses) {
            Optional<List<Object>> recordsOptional =
                    finishesRepository.findAllByStudentIdAndCourseIdInDetail(studentId, course.getCourseId());
            // 没有成绩不计入
            if (recordsOptional.isEmpty()) {
                log.info("学生ID：" + studentId + " 在课程ID " + course.getCourseId() + " 下无成绩记录");
                break;
            } else {
                scoresOfSubmitRecordsGroupByCourseId.add(recordsOptional.get());
            }
        }
        if (scoresOfSubmitRecordsGroupByCourseId.isEmpty()) {
            throw new MyNotFoundException("该学生无实验项目提交记录");
        } else {
            return scoresOfSubmitRecordsGroupByCourseId;
        }
    }


    /**
     * 以下三个接口本身是给前端调用的，但是由于组件库的原因，把二维数组转json数组实在是太麻烦了，所以在后端修改数据返回的格式，
     * 把以下三个接口合并成一个接口进行返回。
     */
    public List<Object> getAllFinishedRecordsByTaskId(Integer taskId) {
        Optional<List<Object>> recordsOptional = finishesRepository.findAllSubmitRecordsByTaskId(taskId);
        if (recordsOptional.isEmpty()) {
            throw new IllegalStateException("该项目无已提交的学生记录");
        } else {
            return recordsOptional.get();
        }
    }

    public List<Object> getAllUnfinishedRecordsScoreGivenByTaskId(Integer taskId) {
        Optional<List<Object>> recordsOptional = finishesRepository.findAllUnfinishedRecordsByTaskId(taskId);
        if (recordsOptional.isEmpty()) {
            throw new IllegalStateException("该项目无已给分的未提交的学生记录");
        } else {
            return recordsOptional.get();
        }
    }

    public List<UserEntity> getAllUnfinishedRecordsScoreNotGivenByTaskId(Integer taskId) {
        Optional<List<UserEntity>> recordOptional = finishesRepository.findAllUnfinishedStudentsByTaskId(taskId);
        if (recordOptional.isEmpty()) {
            throw new IllegalStateException("该项目无未给分的未提交的学生记录");
        } else {
            return recordOptional.get();
        }
    }

    /**
     * 由以上三个接口合并的接口
     */
    public List<JSONObject> getAllFinishedOrUnfinishedRecordsByTaskId(Integer taskId) {
//        Optional<List<Object>>
        return null;
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
