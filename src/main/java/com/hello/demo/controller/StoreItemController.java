package com.hello.demo.controller;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.service.MemberService;
import com.hello.demo.service.StoreItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class StoreItemController {
    private final StoreItemService storeItemService;
    private final MemberService memberService;


    @GetMapping("/items")
    public String items(@AuthenticationPrincipal User user, Model model, @RequestParam(value="page", defaultValue="1") int page) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
        }

        List<StoreItemDTO> storeItems = storeItemService.getAllItems(page);
        model.addAttribute("items", storeItems);
        model.addAttribute("page", page);
        return "items";
    }

    @GetMapping("/myItems")
    public String myItems(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
            MemberDTO memberDTO = memberService.findByEmail(user.getUsername()).get();
            List<StoreItemDTO> storeItems = memberDTO.getStoreItemDTOList();
            model.addAttribute("items", storeItems);
        }

        return "myItems";
    }

    @GetMapping("/item")
    public String item(@AuthenticationPrincipal User user, Model model, @RequestParam(value="itemId") String itemId){
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
            model.addAttribute("isMyItem", memberService.isMyItem(memberService.findByEmail(user.getUsername()).get(), itemId));
        }

        StoreItemDTO storeItemDTO = storeItemService.findByItemId(itemId);
        model.addAttribute("item", storeItemDTO);
        return "item";
    }

    @GetMapping("/item/create")
    public String itemCreate(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
        }

        return "createItem";
    }

    @GetMapping("/item/update")
    public String itemUpdate(@AuthenticationPrincipal User user, Model model, @RequestParam(value="itemId") String itemId) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
            boolean isMyItem = memberService.isMyItem(memberService.findByEmail(user.getUsername()).get(), itemId);
            if (isMyItem) {
                StoreItemDTO storeItemDTO = storeItemService.findByItemId(itemId);
                model.addAttribute("item", storeItemDTO);
            } else {
                return "redirect:/error";
            }
        }

        return "updateItem";
    }

    @PostMapping("/api/item/create")
    public ResponseEntity itemCreate(@AuthenticationPrincipal User user, @ModelAttribute StoreItemDTO storeItemDTO) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        MemberDTO memberDTO = memberService.findByEmail(user.getUsername()).get();
        storeItemDTO.setMemberDTO(memberDTO);

        storeItemService.createItem(storeItemDTO);
        return ResponseEntity.ok(storeItemDTO.getItemId());
    }

    @PostMapping("/api/item/update")
    public ResponseEntity itemUpdate(@AuthenticationPrincipal User user, @ModelAttribute StoreItemDTO storeItemDTO) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        if (memberService.isMyItem(memberService.findByEmail(user.getUsername()).get(), storeItemDTO.getItemId())) {
            if (storeItemService.findByItemId(storeItemDTO.getItemId()).isAvailable()) {
                storeItemService.updateItem(storeItemDTO);
                return ResponseEntity.ok(storeItemDTO.getItemId());
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/api/item/delete")
    public ResponseEntity itemDelete(@AuthenticationPrincipal User user, @RequestParam(value="itemId") String itemId) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        if (memberService.isMyItem(memberService.findByEmail(user.getUsername()).get(), itemId)) {
            storeItemService.deleteItem(itemId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/item/update/soldout")
    public ResponseEntity itemSoldOut(@AuthenticationPrincipal User user, @RequestParam(value="itemId") String itemId) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        if (memberService.isMyItem(memberService.findByEmail(user.getUsername()).get(), itemId)) {
            if (storeItemService.findByItemId(itemId).isAvailable()) {
                storeItemService.updateSoldOut(itemId);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
