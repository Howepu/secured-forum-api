package com.titov.securedforumapi.services;

import com.titov.securedforumapi.dto.request.CommentRequest;
import com.titov.securedforumapi.dto.response.CommentResponse;
import com.titov.securedforumapi.dto.response.ListCommentsResponse;
import com.titov.securedforumapi.mapper.CommentMapper;
import com.titov.securedforumapi.models.Comment;
import com.titov.securedforumapi.models.Post;
import com.titov.securedforumapi.models.Role;
import com.titov.securedforumapi.models.User;
import com.titov.securedforumapi.repositories.CommentRepo;
import com.titov.securedforumapi.repositories.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserService userService;
    private final CommentMapper commentMapper;

    public CommentResponse addCommentToPost(long id, CommentRequest request) {
        Post post = postRepo.findPostById(id);

        post.getComments().add(commentMapper.toEntity(request));
        postRepo.save(post);
        Comment comment = commentMapper.toEntity(request);
        comment.setUser(findCurrentUser());
        comment.setPost(post);

        return commentMapper.toCommentResponse(commentRepo.save(comment));
    }

    public ListCommentsResponse findAllCommentsByPost(long id) {
        Post post = postRepo.findPostById(id);

        List<Comment> comments = commentRepo.findCommentsByPostIs(post);

        List<CommentResponse> responses = comments.stream().map(commentMapper::toCommentResponse).toList();

        return new ListCommentsResponse(responses, responses.size());
    }

    public CommentResponse editComment(long id, CommentRequest request) {
        Comment comment = commentRepo.findCommentById(id);

        if (findCurrentUser() != comment.getUser() || !roleAdminIsExisted(comment.getUser())) {
            throw new AccessDeniedException("У вас нет прав для редактирования этого комментария");
        }

        comment.setTitle(request.title());
        comment.setContent(request.content());
        return commentMapper.toCommentResponse(comment);
    }

    public void deleteComment(long id) {
        Comment comment = commentRepo.findCommentById(id);
        if (findCurrentUser() != comment.getUser() || !roleAdminIsExisted(comment.getUser())) {
            throw new AccessDeniedException("У вас нет прав на удаление этого комментария");
        }
        commentRepo.deleteById(id);
    }

    public User findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userService.findByUsername(username);
    }

    private boolean roleAdminIsExisted(User user) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ADMIN")) {
                return true;
            }
            break;
        }
        return false;
    }
}
