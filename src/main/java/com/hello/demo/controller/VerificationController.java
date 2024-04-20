package com.hello.demo.controller;

import com.hello.demo.dto.MemberDTO;
import com.hello.demo.dto.StudentVerificationDTO;
import com.hello.demo.service.MemberService;
import com.hello.demo.service.StudentVerificationService;
import com.hello.demo.util.StudentVerificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class VerificationController {
    private final StudentVerificationService studentVerificationService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 최종적으로 회원가입하는 API입니다.
    @PostMapping("/api/signup")
    public ResponseEntity signup(@ModelAttribute MemberDTO member) {
        if (studentVerificationService.verification(member.getEmail(), member.getUserId())) {
            memberService.join(member);
            // 회원가입이 완료되면 인증 테이블에서 해당 이메일을 삭제합니다.
            studentVerificationService.delete(member.getEmail());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signup/step2")
    public String signupStep2(Model model, @RequestParam(value="email") String email, @RequestParam(value="key") String key) {
        // queryString으로 전달받은 email과 key를 비교해 인증을 진행합니다.
        // key가 일치하지 않으면 에러 페이지로 리다이렉트합니다.
        if (studentVerificationService.verification(email, key)) {
            model.addAttribute("email", email);
            model.addAttribute("key", key);
            return "signup2";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/welcome")
    public String welcome(Model model, @RequestParam(value="username") String username) {
        model.addAttribute("username", username);
        return "welcome";
    }

    @PostMapping("/api/signup/verification")
    public ResponseEntity signupVerification(@ModelAttribute StudentVerificationDTO studentVerificationDTO) {
        // 이메일이 학교 이메일인지 확인합니다.
        if (StudentVerificationUtil.isStudent(studentVerificationDTO.getEmail())) {
            try {
                studentVerificationDTO.setKey(StudentVerificationUtil.createKey());
                studentVerificationService.save(studentVerificationDTO);
                // 이메일 전송 대신 콘솔에 회원가입을 위한 링크를 출력합니다.
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
