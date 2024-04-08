package com.hello.demo.repository;

import com.hello.demo.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    MemberEntity findByUserId(String userId);
    MemberEntity findByEmailAndUserId(String email, String userId);
}
