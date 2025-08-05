package com.titov.securedforumapi.mapper;

import com.titov.securedforumapi.dto.request.RoleRequest;
import com.titov.securedforumapi.dto.response.RoleResponse;
import com.titov.securedforumapi.models.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse roleToRoleResponse(Role role);

    Role roleRequestToRole(RoleRequest request);

}
