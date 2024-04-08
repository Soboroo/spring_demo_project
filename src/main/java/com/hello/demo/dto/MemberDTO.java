package com.hello.demo.dto;

import com.hello.demo.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long pk;
    private String userId;
    private String email;
    private String username;
    private String password;
    private String Role = "Member";

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setPk(memberEntity.getPk());
        memberDTO.setUserId(memberEntity.getUserId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setUsername(memberEntity.getUsername());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setRole(memberEntity.getRole());
        return memberDTO;
    }
}
