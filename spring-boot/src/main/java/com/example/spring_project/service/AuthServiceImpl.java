package com.example.spring_project.service;

import com.example.spring_project.JwtTokenProvider;
import com.example.spring_project.dto.request.SignInRequest;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.SignInResponse;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.exception_handling.DuplicateEmailException;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.model.Role;
import com.example.spring_project.model.Roles;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.RolesRepository;
import com.example.spring_project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public SignInResponse login(SignInRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new SignInResponse(token, roles);
    }

    @Override
    public UserResponse register(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Roles userRole = rolesRepository.findByName(Role.USER)
                .orElseThrow(() -> new NoSuchElementException(
                        "Default role USER not found in database"));

        Set<Roles> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}