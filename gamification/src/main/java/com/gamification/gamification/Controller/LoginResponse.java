package com.gamification.gamification.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class LoginResponse {
    private String message;
    private String jwt;
    private String username;
    private String status;

    // Constructors, getters, and setters
}
