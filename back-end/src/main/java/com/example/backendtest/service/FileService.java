package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.AlreadyExistException;
import com.example.backendtest.exception.TaskNotFoundException;
import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.TaskEntity;
import com.example.backendtest.model.TestContext;
import com.example.backendtest.repository.FileRepository;
import com.example.backendtest.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final FileStorageService fileStorageService;

    public String addFile(FileEntity file) {
        Optional<FileEntity> fileTemp = fileRepository.findById(file.getUrl());
        if (fileTemp.isPresent()) {
            throw new AlreadyExistException("该文件已存在");
        }
         else {
            fileRepository.save(file);
            return "文件加入成功";
        }
    }

    public JSONObject teacherUploadTaskGuide(MultipartFile file, int courseId, int taskId) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException("该实验项目不存在");
        }
        String location = "/taskGuide/" + courseId + "/" + taskId;
        String fileName = fileStorageService.storeToSpecifiedDirectory(file, location);
        log.info("存储的文件名：" + fileName);
        taskOptional.get().setUrl(fileName);
        taskRepository.save(taskOptional.get());
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("msg", "文件上传成功");
        return json;

    }

    public boolean checkExist(Integer id)
    {
        Optional<FileEntity> fileTemp = fileRepository.findById(id);

        return fileTemp.isPresent();
    }

    /**
     *
     * @param excelFile
     * @param fileName
     * @return
     * @throws Exception
     */
    public List<TestContext> showExcelContext(InputStream excelFile, String fileName) throws Exception {
        boolean flag = true;
        //创建Excel工作薄
        Workbook workbook = this.getWorkbook(excelFile,fileName);
        //选择Excel工作表的第一张表
        Sheet sheet = workbook.getSheetAt(0);
       List<TestContext> testContexts =new ArrayList<>();

       for(int nowRow = 2;nowRow <=sheet.getLastRowNum();nowRow ++)
        {
            TestContext testContext = new TestContext();
            Cell cell = sheet.getRow(nowRow).getCell(0);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            testContext.Number = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(1);
            testContext.ProblemContext = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(2);
            testContext.ContextA = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(3);
            testContext.ContextB = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(4);
            testContext.ContextC = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(5);
            testContext.ContextD = cell.getStringCellValue();
            cell = sheet.getRow(nowRow).getCell(6);
            testContext.Answer = cell.getStringCellValue();
            testContexts.add(testContext);
        }

        //获取到了workbook后就可以在这干你想干的事了
        //Sheet sheet = workbook.getSheetAt(i);获取第i个sheet
        //sheet.getFirstRowNum()
        //sheet.getLastRowNum()
        //Row row = sheet.getRow(i)获取sheet中的第i行
        //Workbook、Sheet、Row、Cell的一些方法可以百度一下

        return testContexts;
    }

    /**
     * 判断文件格式.xls/.xlsx
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream in, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(".xls".equals(fileType)){
            workbook = new HSSFWorkbook(in);
        }else if(".xlsx".equals(fileType)){
            workbook = new XSSFWorkbook(in);
        }else {
            throw new Exception("请上传.xls/.xlsx格式文件！");
        }
        return workbook;
    }
}
