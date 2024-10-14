package org.authservice.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.authservice.mapper.UserMapper;
import org.authservice.model.Role;
import org.authservice.model.User;
import org.authservice.model.dto.SignUpResponse;
import org.authservice.model.dto.UserCredentialsDto;
import org.authservice.repository.UserRepository;
import org.authservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.authservice.util.ExceptionConstants.USER_EXISTS_EXCEPTION_MESSAGE;
import static org.authservice.util.ExceptionConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public SignUpResponse signUp(UserCredentialsDto request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new EntityExistsException(USER_EXISTS_EXCEPTION_MESSAGE.formatted(request.getUsername()));
        }
        var user = mapper.toEntity(request);
        user.setRole(Role.ROLE_USER);
        user = repository.save(user);
        return mapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE.formatted(username)));

    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserCredentialsDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserCredentialsDto findById(Long id) {
        var user = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public UserCredentialsDto update(UserCredentialsDto dto, Long id) {
        var user = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        user = mapper.update(user, dto);
        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var user = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        repository.delete(user);
    }
}
