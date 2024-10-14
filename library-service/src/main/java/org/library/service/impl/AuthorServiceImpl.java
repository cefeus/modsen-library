package org.library.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.library.mapper.AuthorMapper;
import org.library.model.dto.AuthorDto;
import org.library.repository.AuthorRepository;
import org.library.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.library.util.ExceptionMessagesConstants.AUTHOR_EXISTS_EXCEPTION_MESSAGE;
import static org.library.util.ExceptionMessagesConstants.AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper mapper;
    private final AuthorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto findById(Long id) {
        var author = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));
        return mapper.toResponse(author);
    }

    @Override
    public AuthorDto findByName(String name) {
        var author = repository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE.formatted(name)));
        return mapper.toResponse(author);
    }

    @Override
    @Transactional
    public AuthorDto save(AuthorDto dto) {
        if (repository.findByName(dto.name()).isPresent())
            throw new EntityExistsException(AUTHOR_EXISTS_EXCEPTION_MESSAGE.formatted(dto.name()));

        var author = mapper.toAuthor(dto);
        repository.save(author);
        return mapper.toResponse(author);
    }

    @Override
    @Transactional
    public AuthorDto update(Long id, AuthorDto dto) {
        var author = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));
        mapper.update(author, dto);
        return mapper.toResponse(author);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var author = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        repository.delete(author);
    }
}
