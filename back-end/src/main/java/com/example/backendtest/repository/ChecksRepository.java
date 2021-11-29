package com.example.backendtest.repository;

import com.example.backendtest.model.ChecksEntity;
import com.example.backendtest.model.ChecksEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecksRepository extends JpaRepository<ChecksEntity, ChecksEntityPK> {
}
