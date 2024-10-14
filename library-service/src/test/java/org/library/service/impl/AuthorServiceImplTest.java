package org.library.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.mapper.AuthorMapper;
import org.library.model.Author;
import org.library.model.dto.AuthorDto;
import org.library.repository.AuthorRepository;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Spy
    private AuthorMapper mapper = Mappers.getMapper(AuthorMapper.class);
    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;


    @Test
    void findById_shouldReturnCorrectAuthorDto() {
        Long id = 1L;
        Optional<Author> optionalAuthor = Optional.of(Author.builder().id(id).name("Author").build());

        doReturn(optionalAuthor).when(repository).findById(id);

        AuthorDto dto = service.findById(id);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalAuthor.get().getName());
    }

    @Test
    void findById_shouldThrowEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void findByName_shouldReturnAuthorDtoWithName() {
        String name = "name";
        Optional<Author> optionalAuthor = Optional.of(Author.builder().id(1L).name(name).build());

        doReturn(optionalAuthor).when(repository).findByName(name);

        AuthorDto dto = service.findByName(name);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalAuthor.get().getName());
    }

    @Test
    void findByName_shouldThrowEntityNotFound() {
        String name = "name";
        doReturn(Optional.empty()).when(repository).findByName(name);

        assertThatThrownBy(() -> service.findByName(name)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void save_shouldInvokeRepoOneTimeAndSaveEntityWithoutId() {
        AuthorDto dto = new AuthorDto("name");
        doReturn(Optional.empty()).when(repository).findById(1L);
        service.save(dto);
        verify(repository, times(1)).save(argThat(x -> Objects.isNull(x.getId())));
    }

    @Test
    void save_shouldThrowEntityExistsWhenEntityIsPresent() {
        String name = "name";

        doReturn(Optional.of(new Author())).when(repository).findByName(name);

        assertThatThrownBy(() -> service.save(new AuthorDto(name))).isInstanceOf(EntityExistsException.class);
    }

    @Test
    void update_shouldReturnUpdatedAuthor() {
        LocalDateTime time = LocalDateTime.now();
        String name = "name";
        Long id = 1L;
        AuthorDto dto = new AuthorDto(name);
        Author author = Author.builder().id(1L).name(name).build();

        doReturn(Optional.of(author)).when(repository).findByName(name);

        AuthorDto actual = service.update(id , dto);
        assertThat(actual.name()).isEqualTo(author.getName());
    }

    @Test
    void delete_shouldInvokeDeleteOnce() {
        doReturn(Optional.of(new Author())).when(repository).findById(1L);

        service.delete(1L);

        verify(repository, times(1)).delete(new Author());
    }

    @Test
    void delete_shouldThrowEntityNotFound() {
        Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
    }
}