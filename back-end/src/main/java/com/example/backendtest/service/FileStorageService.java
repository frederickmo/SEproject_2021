package com.example.backendtest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.exception.FileStorageException;
import com.example.backendtest.exception.MyFileNotFoundException;
import com.example.backendtest.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("无法创建目录", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            //检查文件名是否含有非法字符
            if (fileName.contains((".."))) {
                throw new FileStorageException("文件名含有非法路径序列" + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("存储文件" + fileName + "失败，请重新上传" , e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("未找到文件" + fileName);
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("未找到文件" + fileName, e);
        }
    }

    public Path loadFileAsPath(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return filePath;
            } else {
                throw new MyFileNotFoundException("未找到文件" + fileName);
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("未找到文件" + fileName, e);
        }
    }

    public List<JSONObject> getAllFiles() {
        File file = new File(fileStorageLocation.toString());
        File[] fileList = file.listFiles();
        List<JSONObject> fileNameList = new ArrayList<>();

        if (fileList == null) {
            JSONObject json = new JSONObject();
            json.put("name", "empty directory");
            fileNameList.add(json);
        } else {
            for (File value : fileList) {
                if (value.isFile() || value.isDirectory()) {
                    String fileName = value.getName();
                    JSONObject json = new JSONObject();
                    json.put("name", fileName);
                    fileNameList.add(json);
                }
            }
        }
        return fileNameList;
    }

}
