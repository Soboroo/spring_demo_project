package com.hello.demo.service;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.entity.StoreItemEntity;
import com.hello.demo.repository.StoreItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreItemService {
    private final StoreItemRepository storeItemRepository;

    public List<StoreItemDTO> getRecentItems() {
        List<StoreItemDTO> storeItemDTOList = new ArrayList<>();
        List<StoreItemEntity> storeItemEntityList = storeItemRepository.findTop8ByOrderByCreatedAtDesc();
        for (StoreItemEntity storeItemEntity : storeItemEntityList) {
            storeItemDTOList.add(StoreItemDTO.toStoreItemDTO(storeItemEntity, MemberDTO.toMemberDTO(storeItemEntity.getMemberEntity())));
        }
        return storeItemDTOList;
    }

    public void createItem(StoreItemDTO storeItemDTO) {
        StoreItemEntity storeItemEntity = StoreItemEntity.toStoreItemEntity(storeItemDTO);
        storeItemRepository.save(storeItemEntity);
    }
}
