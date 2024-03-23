package com.hello.demo.entity;

import com.hello.demo.dto.StudentVerificationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "student_verification")
public class StudentVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "uuid")
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "verification_key")
    private String key;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(System.currentTimeMillis());

    @Column(name = "expired_at")
    private Date expiredAt = new Date(System.currentTimeMillis() + 20 * 60 * 1000);

    public static StudentVerificationEntity toStudentVerificationEntity(StudentVerificationDTO studentVerificationDTO) {
        StudentVerificationEntity studentVerificationEntity = new StudentVerificationEntity();
        studentVerificationEntity.setPk(studentVerificationDTO.getPk());
        studentVerificationEntity.setUuid(studentVerificationDTO.getUuid());
        studentVerificationEntity.setEmail(studentVerificationDTO.getEmail());
        studentVerificationEntity.setKey(studentVerificationDTO.getKey());
        studentVerificationEntity.setCreatedAt(studentVerificationDTO.getCreatedAt());
        studentVerificationEntity.setExpiredAt(studentVerificationDTO.getExpiredAt());
        return studentVerificationEntity;
    }
}
