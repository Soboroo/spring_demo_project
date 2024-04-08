package com.hello.demo.service;

import com.hello.demo.entity.MemberEntity;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberEntity> find = memberService.findByEmail(email);
        MemberEntity member = find.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
        System.out.printf("MemberDetailService.loadUserByUsername: member = %s\n", member);

//        // 권한 정보 등록
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority(member.getRole()));
//
//        // AccountContext 생성자로 UserDetails 타입 생성
//        MemberContext memberContext = new MemberContext(MemberDTO.toMemberDTO(member), roles);
//        return memberContext;

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
