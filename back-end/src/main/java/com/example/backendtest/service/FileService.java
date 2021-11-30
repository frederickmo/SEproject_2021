package com.example.backendtest.service;

import com.example.backendtest.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

}
