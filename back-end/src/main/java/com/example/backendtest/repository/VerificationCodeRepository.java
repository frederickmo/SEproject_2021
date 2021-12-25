package com.example.backendtest.repository;

import com.example.backendtest.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
}
