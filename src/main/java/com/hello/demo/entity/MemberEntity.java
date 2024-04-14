package com.hello.demo.entity;

import com.hello.demo.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String userId;
    private String email;
    private String username;
    private String password;
    private String role = "Member"; // 가지고 있는 권한 정보

    @OneToMany(mappedBy = "memberEntity")
    private List<StoreItemEntity> storeItemEntities = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPk(memberDTO.getPk());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setRole(memberDTO.getRole());
        if (memberDTO.getStoreItemDTOList() != null && !memberDTO.getStoreItemDTOList().isEmpty())
        memberEntity.setStoreItemEntities(memberDTO.getStoreItemDTOList().stream().map(StoreItemEntity::toStoreItemEntity).toList());
        return memberEntity;
    }
}
