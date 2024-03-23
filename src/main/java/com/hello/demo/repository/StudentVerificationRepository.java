package com.hello.demo.repository;

import com.hello.demo.entity.StudentVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentVerificationRepository extends JpaRepository<StudentVerificationEntity, Long> {
    Optional<StudentVerificationEntity> findByEmail(String studentEmail);
}
