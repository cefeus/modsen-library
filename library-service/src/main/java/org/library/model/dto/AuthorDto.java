package org.library.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthorDto(
        @NotEmpty
        String name
) {
}
