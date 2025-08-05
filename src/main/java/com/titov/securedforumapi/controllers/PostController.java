package com.titov.securedforumapi.controllers;

import com.titov.securedforumapi.dto.request.AddPostRequest;
import com.titov.securedforumapi.dto.response.ListPostsResponse;
import com.titov.securedforumapi.dto.response.PostResponse;
import com.titov.securedforumapi.services.PostService;
import com.titov.securedforumapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping
    public ResponseEntity<ListPostsResponse> getAllPosts() {
        ListPostsResponse posts = postService.getAllPosts();
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody AddPostRequest postRequest) {

        PostResponse response = postService.createPost(postRequest);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        PostResponse response = postService.getPostById(id);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable long id, @RequestBody AddPostRequest postRequest) {

        PostResponse updatedPost = postService.updatePostById(id, postRequest);
        
        return ResponseEntity.ok().body(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable long id) {
        postService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }
}
