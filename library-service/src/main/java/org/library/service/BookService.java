package org.library.service;

import org.library.model.dto.request.BookRequestDto;
import org.library.model.dto.response.BookResponseDto;
import org.library.model.dto.response.CreationBookResponseDto;

import java.util.List;

public interface BookService {
    List<BookResponseDto> findAll();
    BookResponseDto findById(Long id);
    BookResponseDto findByIsbn(String isbn);
    CreationBookResponseDto save(BookRequestDto dto);
    BookResponseDto update(Long id, BookRequestDto dto);
    void delete(Long id);
}
