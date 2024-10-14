package org.librarytrackingservice.service;

import org.librarytrackingservice.model.dto.BookReservationCreationRequest;
import org.librarytrackingservice.model.dto.BookReservationCreationResponse;
import org.librarytrackingservice.model.dto.BookReservationDto;

import java.util.List;

public interface BookTrackingService {

    List<BookReservationDto> findAll();
    List<BookReservationDto> findAllAvailable();
    BookReservationDto update(Long id, BookReservationDto dto);

    BookReservationCreationResponse create(BookReservationCreationRequest dto);
}
