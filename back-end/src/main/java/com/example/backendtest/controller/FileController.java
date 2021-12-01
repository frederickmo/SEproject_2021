package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.payload.UploadFileResponse;
import com.example.backendtest.repository.FileRepository;
import com.example.backendtest.service.FileService;
import com.example.backendtest.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "课程资源管理")
@RestController
@RequestMapping("/file")
//@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @ApiOperation("上传单个文件")
    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName,
                    fileDownloadUri,
                    file.getContentType(),
                    file.getSize());
    }

    @ApiOperation("上传文件并重命名")
    @PostMapping("/upload/rename")
    public UploadFileResponse uploadAndRename(@RequestParam("file")MultipartFile file, String newFileName, String location) {
        fileStorageService.storeAndRename(file, newFileName, location);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/downloadFile/" + location + "/")
                .path(newFileName)
                .toUriString();

        return new UploadFileResponse(newFileName,
                fileDownloadUri,
                file.getContentType(),
                file.getSize());

    }

    @ApiOperation("以ID上传头像并重命名")
    @PostMapping("/upload/avatar")
    public UploadFileResponse uploadAvatar(@RequestParam("file")MultipartFile file, @RequestParam Integer id) {
        return fileStorageService.storeAvatar(file, id);
    }

    @ApiOperation("上传多个文件")
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    /**
     * 以下是获取根目录文件的API
     */

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

    @ApiOperation("获取根目录以下两层某目录的文件")
    @GetMapping("/download/{dirName1}/{dirName2}/{fileName:.+}")
    public ResponseEntity<Resource> download2(@PathVariable String fileName,
                                              @PathVariable String dirName1,
                                              @PathVariable String dirName2,
                                              HttpServletRequest request) {
        String dirName = dirName1 + "/" + dirName2;
        return fileStorageService.downloadFile(fileName, dirName, request);
    }

    @ApiOperation("获取某一目录所有文件")
    @ApiImplicitParam(name = "path", value = "路径值（包含左'/'不含右'/'）")
    @GetMapping("/getAll")
    public List<JSONObject> getAllFiles(String path) {
        return fileStorageService.getAllFiles(path);
    }


}
