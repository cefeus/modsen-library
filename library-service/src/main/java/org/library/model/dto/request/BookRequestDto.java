package org.library.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.library.model.dto.AuthorDto;
import org.library.model.dto.GenreDto;

import java.util.List;

public record BookRequestDto(
        @NotEmpty
        String isbn,
        @NotEmpty
        String name,
        @NotEmpty
        String description,

        List<AuthorDto> authors,
        List<GenreDto> genres
) {
}
