package com.gamification.gamification.Controller;

import com.gamification.gamification.Entity.User;
import com.gamification.gamification.Repository.UserRepository;
import com.gamification.gamification.util.JwtUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().body("User registered successfully");
    }



    @PostMapping("/userlogin")
    public ResponseEntity<?> login(@RequestBody User user) {
        System.out.println(userRepository.findByUsername(user.getUsername()));
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if (!userOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            return ResponseEntity.ok(new LoginResponse("Username does not exist", "--",user.getUsername(),"s02"));
        }
        System.out.println(userOptional+"userOptional");
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(new LoginResponse("User authenticated successfully", jwt,user.getUsername(),"s01"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

//            return ResponseEntity.ok(new LoginResponse("Username does not exist", "--",user.getUsername(),"s02"));
        }
    }

}


