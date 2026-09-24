package org.faccordoba.springcloud.msvc.reservacancha.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public record Rol (@Id Integer id, String rol){
}
