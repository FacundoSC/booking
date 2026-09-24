package org.faccordoba.springcloud.msvc.reservacancha.repository;

import org.faccordoba.springcloud.msvc.reservacancha.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository  extends JpaRepository<Rol, Integer> {
    @Query("SELECT r FROM Rol r WHERE rol = :rol")
    Rol findByRol(@Param("rol") String rol);
}
