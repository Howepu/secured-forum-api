package com.titov.securedforumapi.dto.response;

public record PostResponse(long id, String title, String content, UserResponse userResponse) {
}
