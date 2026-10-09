package org.faccordoba.springcloud.msvc.booking.repository;

import org.faccordoba.springcloud.msvc.booking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository  extends JpaRepository<Role, Integer> {

}