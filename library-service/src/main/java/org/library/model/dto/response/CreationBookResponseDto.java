package org.library.model.dto.response;

import org.library.model.dto.AuthorDto;
import org.library.model.dto.GenreDto;

import java.util.List;

public record CreationBookResponseDto(
         Long id,
         String isbn,
         String name,
         String description,
         List<AuthorDto>authors,
         List<GenreDto> genres
) {
}
