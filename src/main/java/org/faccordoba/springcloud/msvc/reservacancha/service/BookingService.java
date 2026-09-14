package org.faccordoba.springcloud.msvc.reservacancha.service;

import org.faccordoba.springcloud.msvc.reservacancha.domain.Booking;
import org.faccordoba.springcloud.msvc.reservacancha.domain.BookingStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {

    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1L);

    public synchronized Booking createBooking(Long facilityId, String username, LocalDate date, LocalTime start, LocalTime end) {
        // prevent overlapping bookings for same facility
        for (Booking b : bookings) {
            if (b.getFacilityId().equals(facilityId) && b.getDate().equals(date) && timesOverlap(b.getStartTime(), b.getEndTime(), start, end)) {
                throw new IllegalStateException("Horario ya reservado");
            }
        }
        Booking nb = new Booking(nextId.getAndIncrement(), facilityId, username, date, start, end);
        nb.setStatus(BookingStatus.PENDIENTE);
        bookings.add(nb);
        return nb;
    }

    private boolean timesOverlap(LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
        return !aEnd.isBefore(bStart) && !bEnd.isBefore(aStart);
    }

    public List<Booking> findByUser(String username) {
        List<Booking> res = new ArrayList<>();
        for (Booking b : bookings) if (b.getUsername().equals(username)) res.add(b);
        return res;
    }

    public List<Booking> findAll() { return new ArrayList<>(bookings); }
}
