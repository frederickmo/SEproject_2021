package com.example.backendtest.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.TestContext;
import com.example.backendtest.payload.UploadFileResponse;
import com.example.backendtest.service.FileService;
import com.example.backendtest.service.FileStorageService;
import com.example.backendtest.service.ManagesService;
import com.example.backendtest.service.TakesService;
import com.example.backendtest.util.FtpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "课程资源管理")
@RestController
@RequestMapping("/file")
@AllArgsConstructor
@CrossOrigin
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

//    @Autowired
    private FileStorageService fileStorageService;

//    @Autowired
    private FileService fileService;

//    @Autowired
    private ManagesService managesService;

//    @Autowired
    private TakesService takesService;

    @SaCheckLogin
    @ApiOperation("上传单个文件")
    @PostMapping("/upload")
    public JSONObject uploadFile(@RequestParam("file")MultipartFile file) {
        return fileStorageService.uploadFile(file);
    }

    @SaCheckLogin
    @ApiOperation("上传文件到指定位置")
    @ApiImplicitParam(name = "location", value = "路径值（包含左'/'不含右'/'）")
    @PostMapping("/upload/redirect")
    public JSONObject uploadFileToSpecifiedDirectory(@RequestParam("file")MultipartFile file, String location) {
        return fileStorageService.uploadFileToSpecifiedDirectory(file, location);
    }

    @SaCheckLogin
    @ApiOperation("上传文件到指定位置并重命名")
    @ApiImplicitParam(name = "location", value = "路径值（包含左'/'不含右'/'）")
    @PostMapping("/upload/redirect/rename")
    public JSONObject uploadFileToSpecifiedDirectoryAndRename(@RequestParam("file")MultipartFile file, String newFileName, String location) {
        // location 带左'/'不带右'/'
        return fileStorageService.uploadFileToSpecifiedDirectoryAndRename(file, newFileName, location);
    }

    @SaCheckLogin
    @ApiOperation("以ID上传头像并重命名")
    @PostMapping("/upload/avatar")
    public UploadFileResponse uploadAvatar(@RequestParam("file")MultipartFile file, @RequestParam Integer id) {
        return fileStorageService.storeAvatar(file, id);
    }

    @SaCheckLogin
    @ApiOperation("上传多个文件")
    @PostMapping("/uploadMultipleFiles")
    public List<JSONObject> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @SaCheckLogin
    @ApiOperation("教师接口 - 上传实验说明文档")
    @PostMapping("/upload/taskGuide")
    public JSONObject teacherUploadTaskGuide(@RequestParam("file")MultipartFile file, int courseId, int taskId) {
        return fileService.teacherUploadTaskGuide(file, courseId, taskId);
    }

    /**
     * 以下是获取根目录文件的API
     */

//    @SaCheckLogin
    @ApiOperation("下载根目录文件")
    @GetMapping("/download/{fileName:.+}")
//    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 HttpServletRequest request) {
        return fileStorageService.downloadFile(fileName, "", request);
    }

    /**
     * 这个是可以获取文件根目录以下一层目录文件的API
     *
     * @param fileName 文件名（包含完整后缀）
     * @param dirName 目录名（不要带'/'）
     * @param request http请求
     * @return 源文件
     */
//    @SaCheckLogin
    @ApiOperation("下载根目录以下某个目录的文件")
    @GetMapping("/download/{dirName}/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, @PathVariable String dirName, HttpServletRequest request) {
        return fileStorageService.downloadFile(fileName, dirName, request);
    }

    /**
     * 暂时写到两层目录
     * 如果需要多层目录的话，就调用fileStorageService的downloadFile这个接口，
     * 然后把dirName拼一下应该就可以了
     * 注意有几层目录，@PathVariable的dirName就得写几个
     */
//    @SaCheckLogin
    @ApiOperation("获取根目录以下两层某目录的文件")
    @GetMapping("/download/{dirName1}/{dirName2}/{fileName:.+}")
    public ResponseEntity<Resource> download2(@PathVariable String fileName,
                                              @PathVariable String dirName1,
                                              @PathVariable String dirName2,
                                              HttpServletRequest request) {
        String dirName = dirName1 + "/" + dirName2;
        return fileStorageService.downloadFile(fileName, dirName, request);
    }

//    @SaCheckLogin
    @ApiOperation("获取根目录以下三层某目录的文件")
    @GetMapping("/download/{dirName1}/{dirName2}/{dirName3}/{fileName:.+}")
    public ResponseEntity<Resource> download3(@PathVariable String fileName,
                                              @PathVariable String dirName1,
                                              @PathVariable String dirName2,
                                              @PathVariable String dirName3,
                                              HttpServletRequest request) {
        String dirName = dirName1 + "/" + dirName2 + "/" + dirName3;
        return fileStorageService.downloadFile(fileName, dirName, request);
    }

//    @SaCheckLogin
    @ApiOperation("获取某一目录所有文件")
    @ApiImplicitParam(name = "path", value = "路径值（包含左'/'不含右'/'）")
    @GetMapping("/getAll")
    public List<JSONObject> getAllFiles(String path) {
        return fileStorageService.getAllFiles(path);
    }





    @ApiOperation("上传文件到服务器上")
    @PostMapping("addFile")
    public Object addFile(int uploadID,int courseID,String addPath,@RequestParam("myFile")MultipartFile multipartFile) throws IOException {


        FileEntity file = new FileEntity();
        int tempID;
        for(;;)
        {
            tempID = (int) (Math.random() * 999);
            if(fileService.checkExist(tempID)==false){break;}
        }
        file.setId(tempID);
        file.setUrl(addPath+"/"+multipartFile.getOriginalFilename());
        if(managesService.checkExist(uploadID,courseID).getBooleanValue("boolean") == false)//检查是否有权利上传资料
        {
            return "manages wrong";
        }
        else {
            fileService.addFile(file);
            //调用自定义的FTP工具类上传文件
            String fileName = FtpUtil.uploadFile(addPath, multipartFile);
            //
            return fileName;
        }
    }

    @ApiOperation("删除服务器上的文件")
    @DeleteMapping("deleteFile")
    public Object deleteFile(String path)
    {
        if(FtpUtil.deleteFile(path)==true)
        {return "success delete";}
return "wrong delete";
    }



    @ApiOperation("通过excel文件展示小项目的题目到前端")
    @PostMapping("/changeExcelIntoJson")
    @ResponseBody
    /**
     * 通过excel文件展示小项目的题目到前端
     */
    public Object changeExcelIntoJson(@RequestParam("file")MultipartFile excelFile) throws Exception {
        if (excelFile.isEmpty()) {
            return "Excel is empty";
        } else {
            String fileName = excelFile.getOriginalFilename();//获取文件名
            InputStream in = excelFile.getInputStream();//获取文件输入流
            //回到前端是json格式
             return fileService.showExcelContext(in, fileName);
        }
    }

    /**
     *可以用于放在教师上传下载文件时的验证
     * @param id
     * @param courseId
     * @return JSON
     */
    @ApiOperation("文件上传下载是否符合教师授课关系")
    @GetMapping("/followManages")
    public JSONObject followManages(Integer id,Integer courseId)
    {
      return managesService.checkExist(id,courseId);
    }

    /**
     * 可以用于放在学生上传下载文件时的验证
     * @param id
     * @param courseId
     * @return JSON
     */
    @ApiOperation("文件上传下载是否符合学生选课关系")
    @GetMapping("/followTakes")
    public JSONObject followTakes(Integer id,Integer courseId)
    {
        return takesService.checkExist(id,courseId);
    }

}
