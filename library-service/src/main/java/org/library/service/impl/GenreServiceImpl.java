package org.library.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.library.mapper.GenreMapper;
import org.library.model.dto.GenreDto;
import org.library.repository.GenreRepository;
import org.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.library.util.ExceptionMessagesConstants.*;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreMapper mapper;
    private final GenreRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto findById(Long id) {
        var genre = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(GENRE_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));
        return mapper.toResponse(genre);
    }

    @Override
    public GenreDto findByName(String name) {
        var genre = repository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(GENRE_NOT_FOUND_EXCEPTION_MESSAGE.formatted(name)));
        return mapper.toResponse(genre);
    }

    @Override
    @Transactional
    public GenreDto save(GenreDto dto) {
        if(repository.findByName(dto.name()).isPresent())
            throw new EntityExistsException(GENRE_EXISTS_EXCEPTION_MESSAGE.formatted(dto.name()));

        var genre = mapper.toGenre(dto);
        repository.save(genre);
        return mapper.toResponse(genre);
    }

    @Override
    @Transactional
    public GenreDto update(Long id, GenreDto dto) {
        var genre = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(GENRE_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));

        mapper.update(genre, dto);
        return mapper.toResponse(genre);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var genre = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(GENRE_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        repository.delete(genre);
    }
}
