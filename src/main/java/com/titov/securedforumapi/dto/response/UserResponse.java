package com.titov.securedforumapi.dto.response;

import com.titov.securedforumapi.models.Role;

import java.util.Set;

public record UserResponse(String username, Set<RoleResponse> roles) {
}
