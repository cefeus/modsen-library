package org.library.service;

import org.library.model.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(Long id);

    GenreDto findByName(String name);

    GenreDto save(GenreDto dto);

    GenreDto update(Long id, GenreDto dto);

    void delete(Long id);
}
