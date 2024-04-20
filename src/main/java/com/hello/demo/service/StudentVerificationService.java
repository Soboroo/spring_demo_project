package com.hello.demo.service;

import com.hello.demo.repository.StudentVerificationRepository;
import com.hello.demo.dto.StudentVerificationDTO;
import com.hello.demo.entity.StudentVerificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            if (studentVerificationEntity.getKey().equals(key)) { // 인증 키 일치
                if (studentVerificationEntity.getExpiredAt().getTime() > System.currentTimeMillis()) { // 인증 시간 확인
                    return true;
                } else {
                    studentVerificationRepository.delete(studentVerificationEntity);
                }
            }
        }
        return false;
    }
}
