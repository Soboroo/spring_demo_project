package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberDTO member) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(member);
        memberRepository.save(memberEntity);
    }
}
