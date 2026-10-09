package org.faccordoba.springcloud.msvc.booking.repository;

import org.faccordoba.springcloud.msvc.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
