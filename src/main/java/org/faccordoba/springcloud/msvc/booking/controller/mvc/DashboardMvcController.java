package org.faccordoba.springcloud.msvc.booking.controller.mvc;

import org.faccordoba.springcloud.msvc.booking.model.Facility;
import org.faccordoba.springcloud.msvc.booking.service.BookingService;
import org.faccordoba.springcloud.msvc.booking.service.FacilityService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardMvcController {
    private final FacilityService facilityService;
    private final BookingService bookingService;

    public DashboardMvcController(FacilityService facilityService, BookingService bookingService) {
        this.facilityService = facilityService;
        this.bookingService = bookingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = getUsername(userDetails, model);
        addFacilitiesAndBookingToModel(model, username);
        return "dashboard";
    }

    @PostMapping("/reservas/crear")
    public String createBooking(@RequestParam Integer facilityId,
                                @RequestParam String date,
                                @RequestParam String start,
                                @RequestParam String end,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        String username = getUsername(userDetails, model);
        addFacilitiesAndBookingToModel(model, username);
        bookingService.createBooking(facilityId, username, LocalDate.parse(date), LocalTime.parse(start), LocalTime.parse(end));
        return "redirect:/dashboard";
    }

    private @NonNull String getUsername(UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        model.addAttribute("username", username);
        return username;
    }

    private void addFacilitiesAndBookingToModel(Model model, String username) {
        Map<Integer, String> facilities = facilityService.findAll().stream().collect(Collectors.toMap(Facility::getId, Facility::getName));
        model.addAttribute("facilities", facilities);
        model.addAttribute("bookings", bookingService.findByUser(username));
    }
}
