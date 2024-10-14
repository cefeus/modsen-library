package org.library.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.library.mapper.AuthorMapper;
import org.library.mapper.BookMapper;
import org.library.mapper.GenreMapper;
import org.library.model.Author;
import org.library.model.Book;
import org.library.model.Genre;
import org.library.model.dto.AuthorDto;
import org.library.model.dto.GenreDto;
import org.library.model.dto.request.BookRequestDto;
import org.library.model.dto.response.BookResponseDto;
import org.library.repository.BookRepository;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository repository;
    @Spy
    private BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Spy
    private AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Spy
    private GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void checkFindAll_shouldReturnCorrectMappedList() {

        List<Book> responses = List.of(
                Book.builder()
                        .id(1L)
                        .isbn("1234567890").name("Book").description("Description")
                        .authors(Set.of(Author.builder().id(1L).name("Author").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("horror").build()))
                        .build(),
                Book.builder()
                .id(1L)
                .isbn("1234567890").name("Book").description("Description")
                .authors(Set.of(Author.builder().id(1L).name("Author").build()))
                .genres(Set.of(Genre.builder().id(1L).name("horror").build()))
                .build()
        );

        doReturn(responses).when(repository).findAll();

        List<BookResponseDto> all = service.findAll();

        IntStream.range(0, all.size())
                .forEach(x -> {
                    String actualName = all.get(x).name();
                    String expectedName = responses.get(x).getName();

                    String actualDescription = all.get(x).description();
                    String expectedDescription = responses.get(x).getDescription();

                    assertThat(actualName).isEqualTo(expectedName);
                    assertThat(actualDescription).isEqualTo(expectedDescription);
                    assertThat(all.get(x).authors()).isNotEmpty();
                    assertThat(all.get(x).genres()).isNotEmpty();
                });
    }

    @Test
    void findByIsbn_shouldReturnCorrectBookDto() {
        String isbn = "1234567890";
        Optional<Book> optionalBook = Optional.of(Book.builder()
                .id(1L)
                .isbn(isbn).name("Book").description("Description")
                .authors(Set.of(Author.builder().id(1L).name("Author").build()))
                .genres(Set.of(Genre.builder().id(1L).name("horror").build()))
                .build());

        doReturn(optionalBook).when(repository).findByIsbn(isbn);

        BookResponseDto dto = service.findByIsbn(isbn);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalBook.get().getName());
        assertThat(dto.description()).isNotBlank();
        assertThat(dto.authors()).isNotEmpty();
        assertThat(dto.genres()).isNotEmpty();
    }

    @Test
    void findByIsbn_shouldThrowEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findByIsbn("1234567890");

        assertThatThrownBy(() -> service.findByIsbn("1234567890")).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void findById_shouldReturnCorrectBookDto() {
        Long id = 1L;
        Optional<Book> optionalBook = Optional.of(Book.builder()
                .id(id)
                .isbn("1234567890").name("Book").description("Description")
                .authors(Set.of(Author.builder().id(1L).name("Author").build()))
                .genres(Set.of(Genre.builder().id(1L).name("horror").build()))
                .build());

        doReturn(optionalBook).when(repository).findById(id);

        BookResponseDto dto = service.findById(id);
        String actualName = dto.name();

        assertThat(actualName).isEqualTo(optionalBook.get().getName());
        assertThat(dto.description()).isNotBlank();
        assertThat(dto.authors()).isNotEmpty();
        assertThat(dto.genres()).isNotEmpty();
    }

    @Test
    void findById_shouldThrowEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void update_shouldReturnUpdatedBook() {
        Long id = 1L;
        Set<Author> authors = new HashSet<>();
        authors.add(Author.builder().id(1L).name("Author").build());
        Set<Genre> genres = new HashSet<>();
        genres.add(Genre.builder().id(1L).name("horror").build());
        BookRequestDto dto = new BookRequestDto("1234567890",
                "New Book",
                "New Description",
                List.of(new AuthorDto("Author")),
                List.of(new GenreDto("New Genre")));
        Book book = Book.builder()
                .id(id)
                .isbn("1234567890").name("Book").description("Description")
                .authors(authors)
                .genres(genres)
                .build();

        doReturn(Optional.of(book)).when(repository).findById(id);

        BookResponseDto actual = service.update(id, dto);

        assertThat(actual.name()).isEqualTo(book.getName());
        assertThat(actual.description()).isEqualTo(book.getDescription());
    }

    @Test
    void delete_shouldInvokeDeleteOnce() {
        doReturn(Optional.of(new Book())).when(repository).findById(1L);

        service.delete(1L);

        verify(repository, times(1)).delete(new Book());
    }

    @Test
    void delete_shouldThrowEntityNotFound() {
        Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
    }
}