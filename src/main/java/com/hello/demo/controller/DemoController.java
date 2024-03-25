package com.hello.demo.controller;

import com.hello.demo.dto.StoreItemDTO;
import com.hello.demo.dto.StudentVerificationDTO;
import com.hello.demo.entity.StoreItemEntity;
import com.hello.demo.service.StoreItemService;
import com.hello.demo.service.StudentVerificationService;
import com.hello.demo.util.StudentVerificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DemoController {
    private final StudentVerificationService studentVerificationService;
    private final StoreItemService storeItemService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/items")
    public String items(Model model, @RequestParam(value="page", defaultValue="1") int page) {
        List<StoreItemDTO> storeItemEntities = storeItemService.getRecentItems();
        model.addAttribute("items", storeItemEntities);
        model.addAttribute("page", page);
        return "items";
    }

    @GetMapping("/item")
    public String item(Model model, @RequestParam(value="id") String id){
        model.addAttribute("title", "iPhone 15 Pro Max 256GB");
        model.addAttribute("username", "Yeongyun Woo");
        model.addAttribute("price", "1,500,000");
        model.addAttribute("description", "갤럭시 쓰다가 처음으로 아이폰 샀는데 적응이 안돼서 팝니다. 1달정도 사용했고, 사용감 거의 없습니다.");
        return "item";
    }

    @GetMapping("/item/create")
    public String itemCreate() {
        return "createItem";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signup/step2")
    public String signupStep2(Model model, @RequestParam(value="email") String email, @RequestParam(value="key") String key) {
        if (studentVerificationService.verification(email, key)) {
            model.addAttribute("email", email);
            return "signup2";
        } else {
            return "error";
        }
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("username", "Yeongyun Woo");
        return "welcome";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @PostMapping("/api/signup/verification")
    public ResponseEntity signupVerification(@ModelAttribute StudentVerificationDTO studentVerificationDTO) {
        if (StudentVerificationUtil.isStudent(studentVerificationDTO.getEmail())) {
            try {
                studentVerificationDTO.setKey(StudentVerificationUtil.createKey());
                studentVerificationService.save(studentVerificationDTO);
                System.out.printf("http://localhost:8080/signup/step2?email=%s&key=%s\n", studentVerificationDTO.getEmail(), studentVerificationDTO.getKey());
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
