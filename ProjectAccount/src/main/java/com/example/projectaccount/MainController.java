package com.example.projectaccount;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String showHomePge() {
        System.out.println("main controller");
        return "index";
    }
}
