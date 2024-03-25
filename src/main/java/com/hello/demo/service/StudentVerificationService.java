package com.hello.demo.service;

import com.hello.demo.repository.StudentVerificationRepository;
import com.hello.demo.dto.StudentVerificationDTO;
import com.hello.demo.entity.StudentVerificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StudentVerificationService {
    private final StudentVerificationRepository studentVerificationRepository;

    public void save(StudentVerificationDTO studentVerificationDTO) {
        StudentVerificationEntity studentVerificationEntity = StudentVerificationEntity.toStudentVerificationEntity(studentVerificationDTO);
        studentVerificationRepository.save(studentVerificationEntity);
    }

    public void delete(String email) {
        StudentVerificationEntity studentVerificationEntity = studentVerificationRepository.findByEmail(email).orElse(null);
        if (studentVerificationEntity != null) {
            studentVerificationRepository.delete(studentVerificationEntity);
        }
    }

    public boolean verification(String email, String key) {
        StudentVerificationEntity studentVerificationEntity = studentVerificationRepository.findByEmail(email).orElse(null);
        if (studentVerificationEntity != null) {
            if (studentVerificationEntity.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
