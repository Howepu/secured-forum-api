package com.titov.securedforumapi.services;

import com.titov.securedforumapi.mapper.PostMapper;
import com.titov.securedforumapi.dto.request.AddPostRequest;
import com.titov.securedforumapi.dto.response.ListPostsResponse;
import com.titov.securedforumapi.dto.response.PostResponse;
import com.titov.securedforumapi.models.Post;
import com.titov.securedforumapi.models.User;
import com.titov.securedforumapi.repositories.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private PostMapper mapper;
    private final UserService userService;

    public ListPostsResponse getAllPosts() {
        return mapper.toDTOList(postRepo.findAll());
    }

    public PostResponse createPost(AddPostRequest request) {
        Post post = mapper.addPostRequestToPost(request);
        post.setUser(findCurrentUser());
        return mapper.postToPostResponse(postRepo.save(post));
    }

    public PostResponse getPostById(long id) {
        return mapper.postToPostResponse(postRepo.findPostById(id));
    }

    public PostResponse updatePostById(long id, AddPostRequest newPost) {

        Post post = postRepo.findPostById(id);

        post.setTitle(newPost.title());
        post.setContent(newPost.content());
        return mapper.postToPostResponse(postRepo.save(post));
    }

    public void deletePostById(long id) {
        postRepo.deleteById(id);
    }

    private User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findByUsername(username);
    }
}
