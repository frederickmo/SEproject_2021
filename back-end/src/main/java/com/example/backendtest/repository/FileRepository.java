package com.example.backendtest.repository;

import com.example.backendtest.model.FileEntity;
import com.example.backendtest.model.TakesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    @Query(value = "select t from FileEntity t where t.url = ?1 ")
    public Optional<FileEntity> findById(String url);


    @Query(value = "select t from FileEntity t where t.id = ?1 ")
    public Optional<FileEntity> findById(Integer id);
}
