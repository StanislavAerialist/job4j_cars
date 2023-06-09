package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute User user, Model model) {
        var savedUser = userService.add(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "users/register";
        }
        return "redirect:/index";
    }

    @GetMapping("/users/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/users/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
