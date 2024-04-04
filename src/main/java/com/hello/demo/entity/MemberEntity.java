package com.hello.demo.entity;

import com.hello.demo.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String userId;
    private String email;
    private String username;
    private String password;

    @ElementCollection
    @CollectionTable(name="roles",joinColumns=@JoinColumn(name="user_pk"))
    @Column(name="role")
    private List<String> hasRole = new ArrayList<>(); // 가지고 있는 권한 정보

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPk(memberDTO.getPk());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setHasRole(memberDTO.getHasRole());
        return memberEntity;
    }
}
