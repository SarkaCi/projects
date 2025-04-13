package com.example.clientlistsimulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MockDataController {

    private final MockDataService mockDataService;

    @Autowired
    public MockDataController(MockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("emails", mockDataService.getAllEmails());
        model.addAttribute("emailBodies", mockDataService.getAllEmailBodies());
        model.addAttribute("smss", mockDataService.getAllSmss());
        model.addAttribute("smsBodies", mockDataService.getAllSmsBodies());
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, Model model) {
        model.addAttribute("emails", mockDataService.searchEmails(query));
        model.addAttribute("smss", mockDataService.searchSmss(query));
        return "index";
    }
}




