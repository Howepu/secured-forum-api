package com.titov.securedforumapi.services;

import com.titov.securedforumapi.models.User;
import com.titov.securedforumapi.models.UserPrincipal;
import com.titov.securedforumapi.repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            log.error("User Not Found: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UserPrincipal(user);
    }
}
