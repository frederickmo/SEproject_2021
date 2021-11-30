package com.example.backendtest.controller;

import com.example.backendtest.repository.*;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("实验管理/文件管理")
@RestController
@RequestMapping("task")
@AllArgsConstructor
public class TaskController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TakesRepository takesRepository;
    private final ManagesRepository managesRepository;
    private final FinishesRepository finishesRepository;
    private final ChecksRepository checksRepository;
}
