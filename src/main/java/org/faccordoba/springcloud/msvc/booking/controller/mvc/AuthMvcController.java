package org.faccordoba.springcloud.msvc.booking.controller.mvc;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthMvcController {

    public AuthMvcController() {
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping(value = "/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
            return "redirect:/dashboard";
    }

    @PostMapping(value = "/logout")
    public String doLogout(HttpServletResponse response) {
        return "redirect:/login";
    }

}
