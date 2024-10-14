package org.library.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record GenreDto(
        @NotEmpty
        String name
) {
}
