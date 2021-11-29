package com.example.backendtest.repository;

import com.example.backendtest.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "select s from UserEntity s where s.email = ?1")
    public Optional<UserEntity> findAllByEmail(String email);
}
