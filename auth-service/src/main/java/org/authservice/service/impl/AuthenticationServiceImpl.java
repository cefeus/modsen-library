package org.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.authservice.model.dto.*;
import org.authservice.service.AuthenticationService;
import org.authservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignUpResponse signUp(UserCredentialsDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userService.signUp(request);
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));

        var user = userService.userDetailsService()
                .loadUserByUsername(request.username());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
