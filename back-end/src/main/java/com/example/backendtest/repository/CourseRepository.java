package com.example.backendtest.repository;

import com.example.backendtest.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

    @Query(value = "select c from CourseEntity c where c.name = ?1")
    public Optional<List<CourseEntity>> findAllByName(String name);

    @Query(value = "select c from CourseEntity c where c.year = ?1")
    public Optional<List<CourseEntity>> findAllByYear(Integer year);

    @Query(value = "select c from CourseEntity c where c.year = ?1 and c.semester = ?2")
    public Optional<List<CourseEntity>> findAllByYearAndSemester(Integer year, Integer semester);
}
