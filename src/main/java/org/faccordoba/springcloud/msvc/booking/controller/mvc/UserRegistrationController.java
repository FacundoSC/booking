package org.faccordoba.springcloud.msvc.booking.controller.mvc;

import org.faccordoba.springcloud.msvc.booking.dto.UserRegistrationDto;
import org.faccordoba.springcloud.msvc.booking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto("", "", "", ""));
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserRegistrationDto userDto) {
        String path = "redirect:/register?success=Registration successful!";
        try {
            userService.save(userDto);
        } catch (Exception e) {
            path = "redirect:/register?error='Registration failed!'";
        }
        return path;
    }

}
