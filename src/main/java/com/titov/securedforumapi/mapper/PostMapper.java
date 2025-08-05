package com.titov.securedforumapi.mapper;

import com.titov.securedforumapi.dto.request.AddPostRequest;
import com.titov.securedforumapi.dto.response.ListPostsResponse;
import com.titov.securedforumapi.dto.response.PostResponse;
import com.titov.securedforumapi.models.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "userResponse", source = "user")
    PostResponse postToPostResponse(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post addPostRequestToPost(AddPostRequest addPostRequest);

    // Вместо прямого преобразования, создаем отдельный метод для списка
    List<PostResponse> postsToPostResponses(List<Post> posts);

    // Используем default метод для создания ListPostsResponse
    default ListPostsResponse toDTOList(List<Post> posts) {
        List<PostResponse> responses = postsToPostResponses(posts);
        return new ListPostsResponse(responses, responses.size());
    }

    @InheritInverseConfiguration(name = "addPostRequestToPost")
    AddPostRequest toAddPostRequest(Post post);

    Post toEntity(PostResponse postResponse);
}
