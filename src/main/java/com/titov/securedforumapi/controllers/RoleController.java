package com.titov.securedforumapi.controllers;

import com.titov.securedforumapi.dto.request.RoleRequest;
import com.titov.securedforumapi.dto.response.RoleResponse;
import com.titov.securedforumapi.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest request) {
        return ResponseEntity.status(201).body(roleService.addRole(request));
    }
}
