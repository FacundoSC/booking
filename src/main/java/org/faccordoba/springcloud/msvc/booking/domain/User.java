package org.faccordoba.springcloud.msvc.booking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public record User (@Id Integer id, String username, String password, Boolean enabled, @Column(name = "rol_id") Integer rolId){
}
