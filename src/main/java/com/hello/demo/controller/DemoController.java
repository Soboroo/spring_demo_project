package com.hello.demo.controller;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.entity.MemberEntity;
import com.hello.demo.service.MemberService;
import com.hello.demo.service.StoreItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DemoController {
    private final MemberService memberService;
    private final StoreItemService storeItemService;

    @GetMapping("/")
    @Transactional
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            Optional<MemberDTO> member = memberService.findByEmail(user.getUsername());
            if (member.isPresent()) {
                List<StoreItemDTO> storeItemDTOList = member.get().getStoreItemDTOList();
                storeItemDTOList = storeItemDTOList.stream().filter(StoreItemDTO::isAvailable).toList();
                storeItemDTOList = storeItemDTOList.subList(0, Math.min(storeItemDTOList.size(), 3));
                model.addAttribute("myItem", storeItemDTOList);
                model.addAttribute("sessionUsername", user.getUsername());
                model.addAttribute("nickname", member.get().getUsername());
            }
        }
        List<StoreItemDTO> storeItemDTOList = storeItemService.getRecentItems();
        model.addAttribute("recentItems", storeItemDTOList);

        return "index";
    }

    @GetMapping("/error")
    public String error(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
        }

        return "error";
    }
}
