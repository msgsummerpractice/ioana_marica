package com.example.spring_project.service;

import com.example.spring_project.JwtTokenProvider;
import com.example.spring_project.dto.request.MfaVerifyRequest;
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

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OneTimeTokenService oneTimeTokenService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public SignInResponse login(SignInRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        GenerateOneTimeTokenRequest ottRequest = new GenerateOneTimeTokenRequest(request.getUsername());
        OneTimeToken ott = oneTimeTokenService.generate(ottRequest);

        System.out.println("Mfa code: " + ott.getTokenValue());

        return new SignInResponse(null, null, true,
                "Introduce the MFA code sent.");
    }

    @Override
    public SignInResponse verifyMfa(MfaVerifyRequest request) {

        OneTimeToken consumedToken = oneTimeTokenService.consume(
                new OneTimeTokenAuthenticationToken(request.getToken()));

        if (!consumedToken.getUsername().equals(request.getUsername())) {
            throw new BadCredentialsException("Mfa code invalid for user.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        String token = jwtTokenProvider.generateToken(authentication);

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new SignInResponse(token, roles, false, "Authentication successful.");
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