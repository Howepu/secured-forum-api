package com.titov.securedforumapi.controllers;

import com.titov.securedforumapi.dto.request.CommentRequest;
import com.titov.securedforumapi.dto.response.CommentResponse;
import com.titov.securedforumapi.dto.response.ListCommentsResponse;
import com.titov.securedforumapi.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable long postId, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.addCommentToPost(postId, request);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/api/posts/{id}/comments")
    public ResponseEntity<ListCommentsResponse> getAllCommentsByPost(@PathVariable long id) {
        ListCommentsResponse response = commentService.findAllCommentsByPost(id);

        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponse> editComment(@PathVariable long id, @RequestBody CommentRequest request) {
        CommentResponse response = commentService.editComment(id, request);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
