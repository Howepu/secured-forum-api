package com.titov.securedforumapi.mapper;

import com.titov.securedforumapi.dto.request.CommentRequest;
import com.titov.securedforumapi.dto.response.CommentResponse;
import com.titov.securedforumapi.dto.response.ListCommentsResponse;
import com.titov.securedforumapi.dto.response.ListPostsResponse;
import com.titov.securedforumapi.dto.response.PostResponse;
import com.titov.securedforumapi.models.Comment;
import com.titov.securedforumapi.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CommentRequest commentRequest);

    @Mapping(source = "user", target = "userResponse")
    @Mapping(source = "post", target = "postResponse")
    CommentResponse toCommentResponse(Comment comment);

}
