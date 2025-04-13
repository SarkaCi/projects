package com.example.projectaccount.account;


import com.example.projectaccount.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    private final UserService userService;

    @GetMapping("/account")
    public String showAccountList(Model model) {
        List<Account> listAccount = service.listAll();
        model.addAttribute("listAccount", listAccount);
        return "account";
    }

    @GetMapping("/account/new")
    public String showNewForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("pageTitle", "Add New Account");
        return "account_form";
    }

    @PostMapping("/account/save")
    public String saveAccount(Account account, RedirectAttributes ra) {
        service.save(account);
        ra.addFlashAttribute("message", "The account has been saved successfully.");
        return "redirect:/account";
    }

    @GetMapping("/account/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Account account = service.get(id);
            model.addAttribute("account", account);
            model.addAttribute("pageTitle", "Edit Account (ID: " + id + ")");
            return "account_form";

        } catch (AccountNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/account";

        }
    }

    @GetMapping("/account/delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The account ID " + id + " has been deleted.");

        } catch (AccountNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/account";
    }

}