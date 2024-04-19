package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.entity.StoreItemEntity;
import com.hello.demo.repository.StoreItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreItemService {
    private final StoreItemRepository storeItemRepository;

    public List<StoreItemDTO> getRecentItems() {
        List<StoreItemDTO> storeItemDTOList = new ArrayList<>();
        List<StoreItemEntity> storeItemEntityList = storeItemRepository.findTop4ByOrderByCreatedAtDesc();
        for (StoreItemEntity storeItemEntity : storeItemEntityList) {
            storeItemDTOList.add(StoreItemDTO.toStoreItemDTO(storeItemEntity, MemberDTO.toMemberDTO(storeItemEntity.getMemberEntity())));
        }
        return storeItemDTOList;
    }

    public List<StoreItemDTO> getAllItems(int page) {
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("createdAt").descending());
        List<StoreItemDTO> storeItemDTOList = storeItemRepository.findAll(pageable).stream().map((x) -> StoreItemDTO.toStoreItemDTO(x, MemberDTO.toMemberDTO(x.getMemberEntity()))).toList();
        return storeItemDTOList;
    }

    public void createItem(StoreItemDTO storeItemDTO) {
        StoreItemEntity storeItemEntity = StoreItemEntity.toStoreItemEntity(storeItemDTO, MemberEntity.toMemberEntity(storeItemDTO.getMemberDTO()));
        storeItemRepository.save(storeItemEntity);
    }

    public void updateItem(StoreItemDTO storeItemDTO) {
        Optional<StoreItemEntity> storeItemEntity = storeItemRepository.findByItemId(storeItemDTO.getItemId());
        if (storeItemEntity.isPresent()) { // could be refactored
            if (storeItemDTO.getTitle() != null) storeItemEntity.get().setTitle(storeItemDTO.getTitle());
            if (storeItemDTO.getDescription() != null) storeItemEntity.get().setDescription(storeItemDTO.getDescription());
            if (storeItemDTO.getImageUrl() != null) storeItemEntity.get().setImageUrl(storeItemDTO.getImageUrl());
            if (storeItemDTO.getPrice() != 0) storeItemEntity.get().setPrice(storeItemDTO.getPrice());
            if (storeItemDTO.isAvailable() != storeItemEntity.get().isAvailable()) storeItemEntity.get().setAvailable(storeItemDTO.isAvailable());
            storeItemEntity.get().setUpdatedAt(new Date());
            storeItemRepository.save(storeItemEntity.get());
        } else {
            throw new IllegalArgumentException("Item not found");
        }
    }

    public StoreItemDTO findByItemId(String itemId) {
        Optional<StoreItemEntity> storeItemEntity = storeItemRepository.findByItemId(itemId);
        if (storeItemEntity.isPresent()) {
            return StoreItemDTO.toStoreItemDTO(storeItemEntity.get(), MemberDTO.toMemberDTO(storeItemEntity.get().getMemberEntity()));
        } else {
            throw new IllegalArgumentException("Item not found");
        }
    }

    public void deleteItem(String itemId) {
        storeItemRepository.deleteByItemId(itemId);
    }
}
