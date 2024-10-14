package org.library.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.library.mapper.AuthorMapper;
import org.library.mapper.BookMapper;
import org.library.mapper.GenreMapper;
import org.library.model.Author;
import org.library.model.Book;
import org.library.model.Genre;
import org.library.model.dto.request.BookRequestDto;
import org.library.model.dto.request.BookTrackingRequest;
import org.library.model.dto.response.BookResponseDto;
import org.library.model.dto.response.CreationBookResponseDto;
import org.library.repository.AuthorRepository;
import org.library.repository.BookRepository;
import org.library.repository.GenreRepository;
import org.library.service.BookService;
import org.library.util.ExceptionMessagesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.library.util.ExceptionMessagesConstants.BOOK_EXISTS_EXCEPTION_MESSAGE;
import static org.library.util.ExceptionMessagesConstants.BOOK_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final RestTemplate restTemplate;
    @Value("${spring.books.tracking.url}")
    private String uri;


    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto findById(Long id) {
        var book = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(BOOK_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));
        return mapper.toResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto findByIsbn(String isbn) {
        var book = repository.findByIsbn(isbn).orElseThrow(
                () -> new EntityNotFoundException(BOOK_NOT_FOUND_EXCEPTION_MESSAGE.formatted(isbn)));
        return mapper.toResponse(book);
    }

    @Override
    @Transactional
    public CreationBookResponseDto save(BookRequestDto dto) {
        if (repository.findByIsbn(dto.isbn()).isPresent())
            throw new EntityExistsException(BOOK_EXISTS_EXCEPTION_MESSAGE.formatted(dto.isbn()));

        Book save = mapper.toBook(dto);

        Set<Author> authors = dto.authors().stream().map(author -> authorRepository.findByName(author.name())
                        .orElseGet(() -> authorMapper.toAuthor(author)))
                .collect(Collectors.toSet());
        Set<Genre> genres = dto.genres().stream().map(genre -> genreRepository.findByName(genre.name())
                .orElseGet(() -> genreMapper.toGenre(genre))).collect(Collectors.toSet());

        save.setAuthors(authors);
        save.setGenres(genres);

        Long id = repository.save(save).getId();
        save.setId(id);
        var request = new BookTrackingRequest(save.getId());
        restTemplate.postForObject(uri, request, BookTrackingRequest.class);

        return mapper.toCreationResponse(save);
    }

    @Override
    @Transactional
    public BookResponseDto update(Long id, BookRequestDto dto) {
        Book book = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(BOOK_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));

        book = mapper.update(book, dto);

        Set<Author> authors = dto.authors().stream().map(author -> authorRepository.findByName(author.name())
                        .orElseGet(() -> authorMapper.toAuthor(author)))
                .collect(Collectors.toSet());

        Set<Genre> genres = dto.genres().stream().map(genre -> genreRepository.findByName(genre.name())
                .orElseGet(() -> genreMapper.toGenre(genre))).collect(Collectors.toSet());

        book.setAuthors(authors);
        book.setGenres(genres);

        return mapper.toResponse(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var book = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(BOOK_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id))
        );
        repository.delete(book);
    }
}
