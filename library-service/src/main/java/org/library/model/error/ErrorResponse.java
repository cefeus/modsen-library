package org.library.model.error;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String id,
        String message,
        int statusCode,
        LocalDateTime timestamp
) {
}
