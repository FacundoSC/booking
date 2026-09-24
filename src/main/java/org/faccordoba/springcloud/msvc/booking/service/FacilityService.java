package org.faccordoba.springcloud.msvc.booking.service;

import org.faccordoba.springcloud.msvc.booking.model.Facility;
import org.faccordoba.springcloud.msvc.booking.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService {
    private final FacilityRepository repository;
    private final List<Facility> facilities = new ArrayList<>();

    public FacilityService(FacilityRepository facilityRepository) {
        repository = facilityRepository;
    }

    @PostConstruct
    public void init() {
        facilities.add(new Facility(1, "Fútbol 7", "FUTBOL"));
        facilities.add(new Facility(2, "Tenis Clay Court", "TENIS"));
        facilities.add(new Facility(3, "Multidisciplinaria", "MULTI"));
        repository.saveAll(facilities);
    }

    public List<Facility> findAll() { return repository.findAll(); }

    public Facility findById(Integer id) {
        return repository.findById(id).orElse(new Facility());
    }
}
