package com.hello.demo.dto;

import com.hello.demo.entity.StoreItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StoreItemDTO {
    private Long pk;
    private String itemId = UUID.randomUUID().toString();
    private String title;
    private String description;
    private String imageUrl;
    private int price;
    private boolean isAvailable = true;
    private Date createdAt = new Date();
    private Date updatedAt;

    private MemberDTO memberDTO;

    public static StoreItemDTO toStoreItemDTO(StoreItemEntity storeItemEntity, MemberDTO memberDTO) {
        StoreItemDTO storeItemDTO = new StoreItemDTO();
        storeItemDTO.setPk(storeItemEntity.getPk());
        storeItemDTO.setItemId(storeItemEntity.getItemId());
        storeItemDTO.setTitle(storeItemEntity.getTitle());
        storeItemDTO.setDescription(storeItemEntity.getDescription());
        storeItemDTO.setImageUrl(storeItemEntity.getImageUrl());
        storeItemDTO.setPrice(storeItemEntity.getPrice());
        storeItemDTO.setAvailable(storeItemEntity.isAvailable());
        storeItemDTO.setCreatedAt(storeItemEntity.getCreatedAt());
        storeItemDTO.setUpdatedAt(storeItemEntity.getUpdatedAt());
        storeItemDTO.setMemberDTO(memberDTO);
        return storeItemDTO;
    }
}
