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
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 Spring Security PasswordEncoder

    public void join(MemberDTO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(member);
        memberRepository.save(memberEntity);
    }

    public boolean isMyItem(MemberDTO member, String itemId) {
        Optional<MemberEntity> find = memberRepository.findByEmail(member.getEmail());
        if (find.isEmpty()) {
            return false;
        }
        MemberEntity memberEntity = find.get();
        return memberEntity.getStoreItemEntities().stream().anyMatch(x -> x.getItemId().equals(itemId));
    }

    public Optional<MemberDTO> findByEmail(String email) {
        Optional<MemberEntity> find = memberRepository.findByEmail(email);
        return find.map(MemberDTO::toMemberDTO);
    }
}
