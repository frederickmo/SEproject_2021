package com.example.backendtest.repository;

import com.example.backendtest.model.TakesEntity;
import com.example.backendtest.model.TakesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TakesRepository extends JpaRepository<TakesEntity, TakesEntityPK> {

    /**
     * 符合主码的表的findById()方法传PK类进去不管用，查不到东西
     * 建议直接重新写（而且建议把主码直接分开作为独立参数传入）
     * 不是@Override因为参数不同
     */
    @Query(value = "select t from TakesEntity t where t.studentId = ?1 and t.courseId = ?2")
    public Optional<TakesEntity> findById(Integer studentId, Integer courseId);

    @Query(value = "select t from TakesEntity t where t.studentId = ?1")
    public Optional<List<Integer>> findAllByStudentId(Integer studentId);

    @Query(value = "select t from TakesEntity t where t.courseId = ?1")
    public Optional<List<Integer>> findAllByCourseId(Integer courseId);
}
