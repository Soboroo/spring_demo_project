package com.hello.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/items")
    public String items(Model model) {
        ItemContent[] list = new ItemContent[8];
        for (int i = 0; i < 8; i++) {
            list[i] = new ItemContent();
            list[i].setName("iPhone "+ i +" Pro Max 256GB");
            list[i].setDescription("갤럭시 쓰다가 처음으로 아이폰 샀는데 적응이 안돼서 팝니다. "+ i +"달정도 사용했고, 사용감 거의 없습니다.");
            list[i].setPrice("1,"+ i +"00,000");
            list[i].setId("item" + i);
            list[i].setTime(i);
        }
        model.addAttribute("items", list);
        return "items";
    }

    @GetMapping("/item")
    public String item(Model model) {
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
    public String signupStep2(Model model) {
        model.addAttribute("email", "example@sch.ac.kr");
        return "signup2";
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
}
