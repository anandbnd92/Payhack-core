package com.gamification.gamification.Controller;

import com.gamification.gamification.Entity.User;
import com.gamification.gamification.Repository.UserRepository;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().body("User registered successfully");
    }

//        @PostMapping("/userlogin")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        System.out.println(userRepository.findByUsername(user.getUsername()));
//        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
//
//
//        try {
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            return ResponseEntity.ok(new LoginResponse("User authenticated successfully", user.getUsername()));
//        } catch (AuthenticationException e) {
//            // If authentication fails, return an error response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//    }
//    @PostMapping("/userlogin")
//    public ResponseEntity<?> login(@RequestBody User user) {
//
////        String username = user.getUsername();
//        String isuser = userRepository.isuserExist(user.getUsername());
//        System.out.println("isuser" + isuser);
//
//        if (isuser != null) {
//            // User exists, return success response
//            return ResponseEntity.ok("User exists");
//        } else {
//            // User does not exist, return error response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
//        }
//    }


}


