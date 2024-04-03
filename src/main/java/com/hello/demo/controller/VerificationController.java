package com.hello.demo.controller;

import com.hello.demo.dto.StudentVerificationDTO;
import com.hello.demo.service.StudentVerificationService;
import com.hello.demo.util.StudentVerificationUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class VerificationController {
    private final StudentVerificationService studentVerificationService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/api/login")
    public ResponseEntity login(Model model, HttpServletRequest request) {
        System.out.printf("email: %s, password: %s", request.getParameter("email"), request.getParameter("password"));
        return ResponseEntity.ok().build();
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
