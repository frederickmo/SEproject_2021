package com.example.backendtest.repository;

import com.example.backendtest.model.ManagesEntity;
import com.example.backendtest.model.ManagesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagesRepository extends JpaRepository<ManagesEntity, ManagesEntityPK> {
}
