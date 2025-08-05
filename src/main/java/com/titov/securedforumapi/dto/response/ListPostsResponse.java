package com.titov.securedforumapi.dto.response;

import java.util.List;

public record ListPostsResponse(List<PostResponse> posts, long size) {
}
