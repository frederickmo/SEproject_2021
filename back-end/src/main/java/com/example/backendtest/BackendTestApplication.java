package com.example.backendtest;

import cn.dev33.satoken.SaManager;
import com.example.backendtest.property.FileStorageProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ServletComponentScan(basePackages = "com.example.backendtest.util")
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class BackendTestApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(BackendTestApplication.class, args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }

}
