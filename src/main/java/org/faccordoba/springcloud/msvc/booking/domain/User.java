package org.faccordoba.springcloud.msvc.reservacancha.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public record User (@Id Integer id, String username, String password, Boolean enabled){
}
