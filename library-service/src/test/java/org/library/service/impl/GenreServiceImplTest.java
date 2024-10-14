package org.library.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.mapper.AuthorMapper;
import org.library.mapper.GenreMapper;
import org.library.model.Author;
import org.library.model.Genre;
import org.library.model.dto.AuthorDto;
import org.library.model.dto.GenreDto;
import org.library.repository.AuthorRepository;
import org.library.repository.GenreRepository;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Spy
    private GenreMapper mapper = Mappers.getMapper(GenreMapper.class);
    @Mock
    private GenreRepository repository;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    void findAll() {

    }

    @Test
    void findById_shouldReturnCorrectGenreDto() {
        Long id = 1L;
        Optional<Genre> optionalGenre = Optional.of(Genre.builder().id(id).name("Genre").build());

        doReturn(optionalGenre).when(repository).findById(id);

        GenreDto dto = service.findById(id);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalGenre.get().getName());
    }

    @Test
    void findById_shouldThrowEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void findByName_shouldReturnGenreDtoWithName() {
        String name = "name";
        Optional<Genre> optionalGenre = Optional.of(Genre.builder().id(1L).name(name).build());

        doReturn(optionalGenre).when(repository).findByName(name);

        GenreDto dto = service.findByName(name);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalGenre.get().getName());
    }

    @Test
    void findByName_shouldThrowEntityNotFound() {
        String name = "name";
        doReturn(Optional.empty()).when(repository).findByName(name);

        assertThatThrownBy(() -> service.findByName(name)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void save_shouldInvokeRepoOneTimeAndSaveEntityWithoutId() {
        GenreDto dto = new GenreDto("name");

        service.save(dto);
        verify(repository, times(1)).save(argThat(x -> Objects.isNull(x.getId())));
    }

    @Test
    void save_shouldThrowEntityExistsWhenEntityIsPresent() {
        String name = "name";

        doReturn(Optional.of(new Genre())).when(repository).findByName(name);

        assertThatThrownBy(() -> service.save(new GenreDto(name))).isInstanceOf(EntityExistsException.class);
    }

    @Test
    void update_shouldReturnUpdatedGenre() {
        String name = "name";
        Long id = 1L;
        GenreDto dto = new GenreDto(name);
        Genre genre = Genre.builder().id(1L).name(name).build();

        doReturn(Optional.of(genre)).when(repository).findById(id);

        GenreDto actual = service.update(id , dto);
        assertThat(actual.name()).isEqualTo(genre.getName());
    }

    @Test
    void delete_shouldInvokeDeleteOnce() {
        doReturn(Optional.of(new Genre())).when(repository).findById(1L);

        service.delete(1L);

        verify(repository, times(1)).delete(new Genre());
    }

    @Test
    void delete_shouldThrowEntityNotFound() {
        Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
    }
}