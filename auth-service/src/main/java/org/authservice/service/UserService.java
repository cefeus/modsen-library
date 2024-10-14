package org.authservice.service;

import org.authservice.model.User;
import org.authservice.model.dto.SignUpResponse;
import org.authservice.model.dto.UserCredentialsDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    SignUpResponse signUp(UserCredentialsDto request);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    List<UserCredentialsDto> findAll();

    UserCredentialsDto findById(Long id);

    UserCredentialsDto update(UserCredentialsDto dto, Long id);

    void delete(Long id);
}
