package org.faccordoba.springcloud.msvc.reservacancha.repository;

import org.faccordoba.springcloud.msvc.reservacancha.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   @Query("SELECT u FROM User u WHERE username = :username")
   Optional<User> findByUsername(@Param("username") String username);
}
