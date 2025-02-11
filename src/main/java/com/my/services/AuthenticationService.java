package com.my.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.my.Repository.UserRepository;
import com.my.models.AuthenticationResponse;
import com.my.models.User;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, AuthenticationManager authenticationManager, 
                                 PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public String register(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        repository.save(user); 

        return "User registered successfully"; 
    }

    public AuthenticationResponse login(User request) {
        System.out.println("Authenticating user...");

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        User user = repository.findByUsername(request.getUsername())
        
        
            .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.print(user.getRole());
    

        String token = jwtService.generateToken(user);
        System.out.println(token);

        return new AuthenticationResponse(token, user.getRole());
    }


}
