package org.librarytrackingservice.model.dto;

import java.time.LocalDateTime;

public record BookReservationCreationResponse(
        Long id,
        Long bookId,
        LocalDateTime borrowingTime,
        LocalDateTime returnTime
) {
}
