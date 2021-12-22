package com.example.backendtest.repository;

import com.example.backendtest.model.SignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignRepository extends JpaRepository<SignEntity,Integer> {

}
