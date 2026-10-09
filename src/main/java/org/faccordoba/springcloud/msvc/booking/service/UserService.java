package org.faccordoba.springcloud.msvc.booking.service;

import org.faccordoba.springcloud.msvc.booking.dto.UserRegistrationDto;
import org.faccordoba.springcloud.msvc.booking.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto request);
}
