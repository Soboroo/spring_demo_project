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
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("sessionUsername", user.getUsername());
        }

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
