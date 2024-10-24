package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthenticationRequest;

@RestController
@RequestMapping("/api")
public class SecureDataController {

	@GetMapping("/secure-data")
    public String getSecureData(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello, " + userDetails.getUsername() + "! This is secured data.";
    }

}
