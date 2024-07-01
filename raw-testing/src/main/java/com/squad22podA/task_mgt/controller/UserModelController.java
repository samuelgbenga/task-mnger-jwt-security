package com.squad22podA.task_mgt.controller;


import com.squad22podA.task_mgt.payload.request.UserRegistrationRequest;
import com.squad22podA.task_mgt.service.UserModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserModelController {

    private final UserModelService userModelService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {

        try{
            userModelService.registerUser(registrationRequest);
            return ResponseEntity.ok("User registered successfully. Please check your email to confirm your account");
        } catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }
}
