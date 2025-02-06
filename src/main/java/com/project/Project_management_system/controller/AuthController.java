package com.project.Project_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Project_management_system.config.JwtProvider;
import com.project.Project_management_system.modal.User;
import com.project.Project_management_system.repository.UserRepository;
import com.project.Project_management_system.request.LoginRequest;
import com.project.Project_management_system.response.AuthResponse;
import com.project.Project_management_system.service.CustomUserDetailsImpl;
import com.project.Project_management_system.service.SubscriptionService;

@RestController

@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customeUserDetails;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {

        User isUserExist = userRepository.findByEmail(user.getEmail());

        if (isUserExist != null) {
            throw new IllegalArgumentException("Email already exists with another account");
        }

        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createdUser);

        // Use the injected subscriptionService to create a subscription
        subscriptionService.creaSubscription(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Signup success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        if (authentication == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Signing success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
