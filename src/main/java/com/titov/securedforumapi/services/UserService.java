package com.titov.securedforumapi.services;

import com.titov.securedforumapi.dto.request.AuthRequest;
import com.titov.securedforumapi.dto.request.RoleRequest;
import com.titov.securedforumapi.dto.response.RoleResponse;
import com.titov.securedforumapi.dto.response.UserResponse;
import com.titov.securedforumapi.mapper.RoleMapper;
import com.titov.securedforumapi.mapper.UserMapper;
import com.titov.securedforumapi.models.Role;
import com.titov.securedforumapi.models.User;
import com.titov.securedforumapi.repositories.RoleRepo;
import com.titov.securedforumapi.repositories.UserRepo;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final RoleMapper roleMapper;
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final UserMapper mapper;

    public UserResponse register(AuthRequest request) {
        log.info("Registering user: {}", request.getUsername());
        
        // Проверяем, существует ли уже пользователь с таким именем
        User existingUser = userRepo.findByUsername(request.getUsername());
        if (existingUser != null) {
            log.error("Пользователь с именем {} уже существует", request.getUsername());
            throw new EntityExistsException("Пользователь с таким именем уже существует");
        }
        
        // Создаем нового пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        
        // Инициализируем коллекции для предотвращения NPE
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        
        User savedUser = userRepo.save(user);
        return mapper.userToUserResponse(savedUser);
    }

    public String verify(AuthRequest request) {
        log.info("Attempting to authenticate user: {}", request.getUsername());
        
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );

            if (authentication.isAuthenticated()) {
                User user = findByUsername(request.getUsername());
                if (user == null) {
                    throw new UsernameNotFoundException("Пользователь не найден: " + request.getUsername());
                }
                return jwtService.generateToken(user);
            }
            
            throw new BadCredentialsException("Неверные учетные данные");
        } catch (BadCredentialsException e) {
            log.error("Ошибка аутентификации: {}", e.getMessage());
            throw e;
        }
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public UserResponse addRoleToUser(long id, RoleRequest request) {
        Role role = roleService.findRoleByName(request.name());
        if (role == null) {
            throw new EntityNotFoundException("Роль с именем '" + request.name() + "' не найдена");
        }

        User user = userRepo.findById(id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с ID " + id + " не найден");
        }
        
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        
        user.getRoles().add(role);
        User savedUser = userRepo.save(user);
        return mapper.userToUserResponse(savedUser);
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Текущий пользователь не найден: " + username);
        }
        return mapper.userToUserResponse(user);
    }
}
