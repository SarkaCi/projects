package com.example.projectaccount.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final UserFlowService userFlowService;

    @GetMapping("/user")
    public String showUserList(Model model) {
        List<User> listUser = service.listAll();
        model.addAttribute("listUser", listUser);
        return "user";
    }

    @GetMapping("/user/new")
    public String showNewForm(@RequestParam(name = "accountId") Integer accountId, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        model.addAttribute("accountId", accountId);
        return "user_form";
    }

    @PostMapping("/user/save")
    public String saveUser(UserDto userDto, @RequestParam(name = "accountId") Integer accountId, RedirectAttributes ra, Model model) {
        String message = userFlowService.saveUser(userDto, accountId, ra, model);
        model.addAttribute("message", message);
        return "redirect:/user";
    }


    @GetMapping("/user/show")
    public String showUsersForAccount(@RequestParam(name = "accountId") Integer accountId, Model model) {
        List<User> listUser = service.getUsersForAccount(accountId);
        model.addAttribute("listUser", listUser);
        model.addAttribute("pageTitle", "Manage Users");
        model.addAttribute("accountId", accountId);
        return "user";
    }

    @GetMapping("/user/edit")
    public String showEditForm(@RequestParam(name = "accountId") Integer accountId, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(accountId);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + accountId + ")");
            model.addAttribute("accountId", accountId);
            return "user_form";

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/user";

        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }
}
