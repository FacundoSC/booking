package org.faccordoba.springcloud.msvc.reservacancha.controller.mvc;

import org.faccordoba.springcloud.msvc.reservacancha.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePublicMvcController {

    private final FacilityService facilityService;

    public HomePublicMvcController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping({"/","/home"})
    public String index(Model model) {
        model.addAttribute("facilities", facilityService.findAll());
        return "index";
    }
}
