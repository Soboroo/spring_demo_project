package com.hello.demo.dto;

import com.hello.demo.entity.StudentVerificationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentVerificationDTO {
    private Long pk;
    private String uuid = UUID.randomUUID().toString();
    private String email;
    private String key;
    private Date createdAt = new Date();
    private Date expiredAt = new Date(System.currentTimeMillis() + 20 * 60 * 1000);

    public static StudentVerificationDTO toStudentVerificationDTO(StudentVerificationEntity studentVerificationEntity) {
        StudentVerificationDTO studentVerificationDTO = new StudentVerificationDTO();
        studentVerificationDTO.setPk(studentVerificationEntity.getPk());
        studentVerificationDTO.setUuid(studentVerificationEntity.getUuid());
        studentVerificationDTO.setEmail(studentVerificationEntity.getEmail());
        studentVerificationDTO.setKey(studentVerificationEntity.getKey());
        studentVerificationDTO.setCreatedAt(studentVerificationEntity.getCreatedAt());
        studentVerificationDTO.setExpiredAt(studentVerificationEntity.getExpiredAt());
        return studentVerificationDTO;
    }
}
