package org.faccordoba.springcloud.msvc.reservacancha.service;

import org.faccordoba.springcloud.msvc.reservacancha.domain.Facility;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService {

    private final List<Facility> facilities = new ArrayList<>();

    @PostConstruct
    public void init() {
        facilities.add(new Facility(1L, "Fútbol 7", "FUTBOL"));
        facilities.add(new Facility(2L, "Tenis Clay Court", "TENIS"));
        facilities.add(new Facility(3L, "Multidisciplinaria", "MULTI"));
    }

    public List<Facility> findAll() { return facilities; }

    public Facility findById(Long id) {
        return facilities.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }
}
