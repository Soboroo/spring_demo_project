package com.hello.demo.controller;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StoreItemDTO;
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
// Lombok 어노테이션입니다. IntelliJ에서 사용하기 위해 플러그인 설치 및 추가 설정이 필요합니다.
// Service 클래스를 주입받기 위해 생성자를 만들어주는 어노테이션입니다.
// Spring Boot에서 Singleton으로 생성되는 Bean을 주입받을 때 사용합니다.
// https://gmlwjd9405.github.io/2018/11/29/intellij-lombok.html

// 처음에는 기능 구현이 가능한지 검증할 목적으로 Demo라 이름지었습니다.
// 기획서에는 MainController 하나로 작성되어있으나
// 상품을 다루는 StoreItemController, 회원가입, 로그인 등을 다루는 VerificationController
// 으로 나누어 리펙토링을 진행했습니다.
public class DemoController {
    private final MemberService memberService;
    private final StoreItemService storeItemService;

    @GetMapping("/")
    @Transactional
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) { // 로그인 상태인지 확인하기 위해 이와 같은 if 문을 사용합니다.
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
