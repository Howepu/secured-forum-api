package com.titov.securedforumapi.repositories;

import com.titov.securedforumapi.models.Comment;
import com.titov.securedforumapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPostIs(Post post);

    Comment findCommentById(long id);
}
