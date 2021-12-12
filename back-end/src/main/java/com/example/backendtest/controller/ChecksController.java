package com.example.backendtest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backendtest.model.ChecksEntity;
import com.example.backendtest.service.ChecksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "教师批改记录管理")
@RestController
@AllArgsConstructor
@RequestMapping("/checks")
@CrossOrigin
public class ChecksController {

    private ChecksService checksService;

    @ApiOperation("新增批改记录")
    @PostMapping("/add")
    public JSONObject add(@RequestBody ChecksEntity checks) {
        return checksService.add(checks);
    }
}
