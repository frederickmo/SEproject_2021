package com.example.backendtest.repository;

import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.TakesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakesRepository extends JpaRepository<TakesEntity, TakesEntityPK> {
}
