package com.my.config;

import com.my.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            .csrf(AbstractHttpConfigurer::disable) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/register", "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/departments/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/departments/edit/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/departments/delete/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/departments/getAllDepartments").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/departments/*").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/designations/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/designations/edit/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/designations/delete/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/designations/getAllDesignations").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/designations/*").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/employees/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/unlinked-users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/getAllEmployees").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/employees/edit/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/delete/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/*").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/tasks/add").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/tasks/getAllTasks").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.GET, "/tasks/delete/**").hasRole("ADMIN")
                .requestMatchers("/auth/**", "/tasks/**", "/dashboard/**").authenticated()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
