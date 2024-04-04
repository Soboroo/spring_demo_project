package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.detail.MemberDetail;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email);
        if(member == null)
            throw new UsernameNotFoundException("계정을 찾을 수 없습니다.");
        return new MemberDetail(MemberDTO.toMemberDTO(member));
    }
}
