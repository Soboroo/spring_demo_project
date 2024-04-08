package com.hello.demo.controller;

import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.service.MemberService;
import com.hello.demo.service.StoreItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DemoController {
    private final StoreItemService storeItemService;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) { //if user is logged in
            System.out.println(user.getUsername());
            model.addAttribute("sessionUsername", user.getUsername());
        }

        return "index";
    }

    @GetMapping("/items")
    public String items(@AuthenticationPrincipal User user, Model model, @RequestParam(value="page", defaultValue="1") int page) {
        if (user != null) { //if user is logged in
            System.out.println(user.getUsername());
            model.addAttribute("sessionUsername", user.getUsername());
        }

        List<StoreItemDTO> storeItemEntities = storeItemService.getRecentItems();
        model.addAttribute("items", storeItemEntities);
        model.addAttribute("page", page);
        return "items";
    }

    @GetMapping("/item")
    public String item(@AuthenticationPrincipal User user, Model model, @RequestParam(value="id") String id){
        if (user != null) { //if user is logged in
            System.out.println(user.getUsername());
            model.addAttribute("sessionUsername", user.getUsername());
        }

        model.addAttribute("title", "iPhone 15 Pro Max 256GB");
        model.addAttribute("username", "Yeongyun Woo");
        model.addAttribute("price", "1,500,000");
        model.addAttribute("description", "갤럭시 쓰다가 처음으로 아이폰 샀는데 적응이 안돼서 팝니다. 1달정도 사용했고, 사용감 거의 없습니다.");
        return "item";
    }

    @GetMapping("/item/create")
    public String itemCreate(@AuthenticationPrincipal User user, Model model) {
        if (user != null) { //if user is logged in
            System.out.println(user.getUsername());
            model.addAttribute("sessionUsername", user.getUsername());
        }

        return "createItem";
    }

    @GetMapping("/error")
    public String error(@AuthenticationPrincipal User user, Model model) {
        if (user != null) { //if user is logged in
            System.out.println(user.getUsername());
            model.addAttribute("sessionUsername", user.getUsername());
        }

        return "error";
    }
}
