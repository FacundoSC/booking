package org.faccordoba.springcloud.msvc.booking.service;

import org.faccordoba.springcloud.msvc.booking.model.Booking;
import org.faccordoba.springcloud.msvc.booking.model.BookingStatus;
import org.faccordoba.springcloud.msvc.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {
    private final BookingRepository repository;
    private final AtomicLong nextId = new AtomicLong(1L);

    public BookingService(BookingRepository bookingRepository) {
        repository = bookingRepository;
    }

    public synchronized Booking createBooking(Integer facilityId, String username, LocalDate date, LocalTime start, LocalTime end) {
        // prevent overlapping bookings for same facility
        findAll().forEach(b-> {
            if (b.getFacilityId().equals(facilityId) && b.getDate().equals(date) && timesOverlap(b.getStartTime(), b.getEndTime(), start, end)) {
            throw new IllegalStateException("Horario ya reservado");
        }});
        Booking nb = new Booking(nextId.getAndIncrement(), facilityId, username, date, start, end);
        nb.setStatus(BookingStatus.PENDING);
        return repository.save(nb);
    }

    private boolean timesOverlap(LocalTime aStart, LocalTime aEnd, LocalTime bStart, LocalTime bEnd) {
        return !aEnd.isBefore(bStart) && !bEnd.isBefore(aStart);
    }

    public List<Booking> findByUser(String username) {
        List<Booking> res = new ArrayList<>();
        findAll().forEach(b->{
            if (b.getUsername().equals(username))
                res.add(b);
        });
        return res;
    }

    public List<Booking> findAll() { return repository.findAll();}
}
