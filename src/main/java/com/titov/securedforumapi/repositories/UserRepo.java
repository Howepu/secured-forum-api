package com.titov.securedforumapi.repositories;

import com.titov.securedforumapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findById(long id);
}
