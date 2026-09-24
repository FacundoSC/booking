package org.faccordoba.springcloud.msvc.booking.controller.mvc;

import org.faccordoba.springcloud.msvc.booking.service.BookingService;
import org.faccordoba.springcloud.msvc.booking.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class DashboardMvcController {

    private final FacilityService facilityService;
    private final BookingService bookingService;

    public DashboardMvcController(FacilityService facilityService, BookingService bookingService) {
        this.facilityService = facilityService;
        this.bookingService = bookingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) return "redirect:/login";
        model.addAttribute("username", username);
        model.addAttribute("facilities", facilityService.findAll());
        model.addAttribute("bookings", bookingService.findByUser(username));
        return "dashboard";
    }

    @PostMapping("/reservas/crear")
    public String createBooking(@RequestParam Long facilityId,
                                @RequestParam String date,
                                @RequestParam String start,
                                @RequestParam String end,
                                HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null) return "redirect:/login";
        bookingService.createBooking(facilityId, username, LocalDate.parse(date), LocalTime.parse(start), LocalTime.parse(end));
        return "redirect:/dashboard";
    }
}
