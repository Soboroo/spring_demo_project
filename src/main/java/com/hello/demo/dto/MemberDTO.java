package com.hello.demo.dto;

import com.hello.demo.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "storeItemDTOList") // toMemberDTO 메서드에서 stackoverflow 방지
public class MemberDTO {
    private Long pk;
    private String userId;
    private String email;
    private String username;
    private String password;
    private String Role = "Member";

    private List<StoreItemDTO> storeItemDTOList = new ArrayList<>();

    // 데이터베이스와 통신할 때는 Entity로 변환해서 통신하고, 클라이언트와 통신할 때는 DTO로 변환해서 통신합니다.
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setPk(memberEntity.getPk());
        memberDTO.setUserId(memberEntity.getUserId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setUsername(memberEntity.getUsername());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setRole(memberEntity.getRole());
        if (memberEntity.getStoreItemEntities() != null && !memberEntity.getStoreItemEntities().isEmpty())
            memberDTO.setStoreItemDTOList(memberEntity.getStoreItemEntities().stream().map((x) -> StoreItemDTO.toStoreItemDTO(x, memberDTO)).toList());
        return memberDTO;
    }
}
