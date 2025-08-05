package com.titov.securedforumapi.mapper;

import com.titov.securedforumapi.dto.response.PostResponse;
import com.titov.securedforumapi.dto.response.UserResponse;
import com.titov.securedforumapi.models.Post;
import com.titov.securedforumapi.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    UserResponse userToUserResponse(User user);
}
