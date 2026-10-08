package org.faccordoba.springcloud.msvc.booking.dto;


import java.time.LocalDate;
import java.time.LocalTime;

public record BookingDto(String facility,
        LocalDate date, LocalTime startTime, LocalTime endTime,
        String status) {
}
