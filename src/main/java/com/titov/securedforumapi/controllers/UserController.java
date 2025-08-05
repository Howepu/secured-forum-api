package com.titov.securedforumapi.controllers;

import com.titov.securedforumapi.dto.request.AuthRequest;
import com.titov.securedforumapi.dto.request.RoleRequest;
import com.titov.securedforumapi.dto.response.UserResponse;
import com.titov.securedforumapi.models.User;
import com.titov.securedforumapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AuthRequest request) {
        log.info("Register request received for user: {}", request.getUsername());
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        log.info("Login request received for user: {}", request.getUsername());
        String token = userService.verify(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/api/users/{id}/roles")
    public ResponseEntity<UserResponse> addRoleToUser(@PathVariable long id, @RequestBody RoleRequest request) {
        UserResponse response = userService.addRoleToUser(id, request);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok().body(userService.getCurrentUser());
    }
}
