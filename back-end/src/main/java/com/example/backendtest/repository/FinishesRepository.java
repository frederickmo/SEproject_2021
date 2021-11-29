package com.example.backendtest.repository;

import com.example.backendtest.model.FinishesEntity;
import com.example.backendtest.model.FinishesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishesRepository extends JpaRepository<FinishesEntity, FinishesEntityPK> {
}
