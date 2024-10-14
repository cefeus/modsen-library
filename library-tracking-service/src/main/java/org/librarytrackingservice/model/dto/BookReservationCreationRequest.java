package org.librarytrackingservice.model.dto;


import jakarta.validation.constraints.NotNull;

public record BookReservationCreationRequest(
        @NotNull
        Long bookId
) {
}
