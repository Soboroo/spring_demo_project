package com.hello.demo.entity;

import com.hello.demo.dto.StoreItemDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "store_items")
public class StoreItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    private String itemId = UUID.randomUUID().toString();
    private String title;
    private String description;
    private String imageUrl;
    private int price;
    private boolean isAvailable;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    public static StoreItemEntity toStoreItemEntity(StoreItemDTO storeItemDTO, MemberEntity memberEntity) {
        StoreItemEntity storeItemEntity = new StoreItemEntity();
        storeItemEntity.setPk(storeItemDTO.getPk());
        storeItemEntity.setItemId(storeItemDTO.getItemId());
        storeItemEntity.setTitle(storeItemDTO.getTitle());
        storeItemEntity.setDescription(storeItemDTO.getDescription());
        storeItemEntity.setImageUrl(storeItemDTO.getImageUrl());
        storeItemEntity.setPrice(storeItemDTO.getPrice());
        storeItemEntity.setAvailable(storeItemDTO.isAvailable());
        storeItemEntity.setCreatedAt(storeItemDTO.getCreatedAt());
        storeItemEntity.setUpdatedAt(storeItemDTO.getUpdatedAt());
        storeItemEntity.setMemberEntity(memberEntity);
        return storeItemEntity;
    }
}
