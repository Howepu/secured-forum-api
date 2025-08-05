package com.titov.securedforumapi.dto.response;

import java.util.List;

public record ListCommentsResponse(List<CommentResponse> comments, long size) {
}
