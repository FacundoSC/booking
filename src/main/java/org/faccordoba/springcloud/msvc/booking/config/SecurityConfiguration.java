package org.faccordoba.springcloud.msvc.booking.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserDetailsService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register**", "/js/**", "/css/**", "/img/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/dashboard").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/reservas/crear").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=Invalid%20credentials")
                        .defaultSuccessUrl("/dashboard", true) // Directo al dashboard tras login exitoso
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new org.springframework.security.web.util.matcher.RequestMatcher() {
                            @Override
                            public boolean matches(HttpServletRequest request) {
                                return "POST".equals(request.getMethod()) && "/logout".equals(request.getRequestURI());
                            }
                        })
                        .logoutSuccessUrl("/login?logout=You%20have%20been%20logged%20out%20successfully.")
                        .permitAll()
                );

        return http.build();
    }




}
