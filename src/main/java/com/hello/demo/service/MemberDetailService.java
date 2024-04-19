package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.entity.MemberEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberDTO> find = memberService.findByEmail(email);
        MemberDTO member = find.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
