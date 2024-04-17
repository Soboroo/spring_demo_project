package com.hello.demo.repository;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, Long>{
    List<StoreItemEntity> findTop4ByOrderByCreatedAtDesc();
    Optional<StoreItemEntity> findByItemId(String itemId);
    Optional<StoreItemEntity> findByTitle(String title);
    List<StoreItemEntity> findByMemberEntity(MemberEntity memberEntity);
}
