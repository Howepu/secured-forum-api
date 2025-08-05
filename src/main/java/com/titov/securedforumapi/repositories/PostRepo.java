package com.titov.securedforumapi.repositories;

import com.titov.securedforumapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Post findPostById(long id);
    void deleteById(long id);
}
