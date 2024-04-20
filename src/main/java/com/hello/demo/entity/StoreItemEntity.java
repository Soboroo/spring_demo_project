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
    private boolean isAvailable = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // 일대다 설정시 LazyInitializationException이 발생했습니다.
    // 여러가지 시도한 결과 FetchType.LAZY 사용과 @Transactional의 사용이 도움이 되었습니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    // setMemberEntity에서 toMemberEntity를 사용하면 stackoverflow가 발생합니다.
    // 따라서 setMemberEntity에서는 MemberEntity를 직접 넣어주는 것으로 수정했습니다.
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
