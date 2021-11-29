package com.example.backendtest.repository;

import com.example.backendtest.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
