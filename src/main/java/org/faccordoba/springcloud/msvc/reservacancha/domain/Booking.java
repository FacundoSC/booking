package org.faccordoba.springcloud.msvc.reservacancha.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private Long id;
    private Long facilityId;
    private String username;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus status = BookingStatus.PENDIENTE;

    public Booking() {}

    public Booking(Long id, Long facilityId, String username, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.facilityId = facilityId;
        this.username = username;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFacilityId() { return facilityId; }
    public void setFacilityId(Long facilityId) { this.facilityId = facilityId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public java.time.LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
