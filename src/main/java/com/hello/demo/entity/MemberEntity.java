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
    private String password; // 평문으로 저장하지 않습니다. 클라이언트에서 CryptoJS를 통해 SHA256으로 해싱합니다.
    private String role = "Member"; // 가지고 있는 권한 정보

    @OneToMany(mappedBy = "memberEntity") // 멤버는 여러개의 상품을 가지고 있기에 OneToMany로 설정했습니다.
    @OrderBy("createdAt DESC")
    private List<StoreItemEntity> storeItemEntities = new ArrayList<>();

    // 데이터베이스와 통신할 때는 Entity로 변환해서 통신하고, 클라이언트와 통신할 때는 DTO로 변환해서 통신합니다.
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPk(memberDTO.getPk());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setRole(memberDTO.getRole());
        if (memberDTO.getStoreItemDTOList() != null && !memberDTO.getStoreItemDTOList().isEmpty())
            memberEntity.setStoreItemEntities(memberDTO.getStoreItemDTOList().stream().map((x) -> StoreItemEntity.toStoreItemEntity(x, memberEntity)).toList());
        return memberEntity;
    }
}
