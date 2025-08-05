package com.titov.securedforumapi.dto.response;

public record CommentResponse(long id, String title, String content, UserResponse userResponse,
                              PostResponse postResponse) {
}
