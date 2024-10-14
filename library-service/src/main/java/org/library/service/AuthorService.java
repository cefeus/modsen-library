package org.library.service;

import org.library.model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(Long id);

    AuthorDto findByName(String name);

    AuthorDto save(AuthorDto dto);

    AuthorDto update(Long id, AuthorDto dto);

    void delete(Long id);
}
