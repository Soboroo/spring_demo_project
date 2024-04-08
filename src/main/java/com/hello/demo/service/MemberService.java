package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(MemberDTO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(member);
        memberRepository.save(memberEntity);
    }

    public Optional<MemberEntity> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
