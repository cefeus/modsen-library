package org.library.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookTrackingRequest(
        @NotNull
        Long bookId
) {
}
