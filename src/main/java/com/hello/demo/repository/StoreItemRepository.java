package com.hello.demo.repository;

import com.hello.demo.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, Long>  {
    // find given number of recent items
    List<StoreItemEntity> findTop8ByOrderByCreatedAtDesc();
    // find by item id
    Optional<StoreItemEntity> findByItemId(String itemId);
    // find by item name
    Optional<StoreItemEntity> findByTitle(String title);
    // find by user id
    List<StoreItemEntity> findByUserId(String userId);
    // find by item name and user id
    Optional<StoreItemEntity> findByTitleAndUserId(String title, String userId);


}
