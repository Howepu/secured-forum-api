package com.titov.securedforumapi.services;

import com.titov.securedforumapi.dto.request.RoleRequest;
import com.titov.securedforumapi.dto.response.RoleResponse;
import com.titov.securedforumapi.mapper.RoleMapper;
import com.titov.securedforumapi.models.Role;
import com.titov.securedforumapi.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleMapper mapper;
    private final RoleRepo roleRepo;

    public RoleResponse addRole(RoleRequest request) {
        Role role = mapper.roleRequestToRole(request);
        return mapper.roleToRoleResponse(roleRepo.save(role));
    }

    public Role findRoleByName(String name) {
        return roleRepo.findByName(name);
    }
}
