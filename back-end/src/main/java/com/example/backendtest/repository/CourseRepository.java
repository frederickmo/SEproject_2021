package com.example.backendtest.repository;

import com.example.backendtest.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
}
