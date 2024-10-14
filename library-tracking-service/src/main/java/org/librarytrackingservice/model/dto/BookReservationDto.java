package org.librarytrackingservice.model.dto;

import java.time.LocalDateTime;

public record BookReservationDto(
        Long bookId,
        LocalDateTime borrowingTime,
        LocalDateTime returnTime
) {
}
