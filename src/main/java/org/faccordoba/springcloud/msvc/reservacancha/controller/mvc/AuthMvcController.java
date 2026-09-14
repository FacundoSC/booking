package org.faccordoba.springcloud.msvc.reservacancha.controller.mvc;

import org.faccordoba.springcloud.msvc.reservacancha.config.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthMvcController {

    private final JwtUtils jwtUtils;

    public AuthMvcController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        // Very simple auth stub: accept any username/password
        String token = jwtUtils.generateToken(username);
        Cookie cookie = new Cookie("JWT-TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() { return "register"; }
}
