package org.faccordoba.springcloud.msvc.reservacancha.controller.mvc;

import org.faccordoba.springcloud.msvc.reservacancha.config.JwtUtils;
import org.faccordoba.springcloud.msvc.reservacancha.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserDetailsService userDetailsService;

    public AuthMvcController(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        // Very simple auth stub: accept any username/password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDetails userDetails =userDetailsService.findByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtUtils.generateToken(username);
            Cookie cookie = new Cookie("JWT-TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/dashboard";

        }
        else {
             return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage() { return "register"; }
}
