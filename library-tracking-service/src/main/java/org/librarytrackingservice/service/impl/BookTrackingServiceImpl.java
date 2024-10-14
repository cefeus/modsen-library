package org.librarytrackingservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.librarytrackingservice.model.dto.BookReservationCreationResponse;
import org.librarytrackingservice.model.dto.BookReservationDto;
import org.librarytrackingservice.mapper.BookReservationMapper;
import org.librarytrackingservice.model.dto.BookReservationCreationRequest;
import org.librarytrackingservice.repository.BookReservationRepository;
import org.librarytrackingservice.service.BookTrackingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.librarytrackingservice.util.ExceptionConstants.RESERVATION_NOT_FOUND_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class BookTrackingServiceImpl implements BookTrackingService {

    private final BookReservationRepository repository;
    private final BookReservationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookReservationDto> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookReservationDto> findAllAvailable() {
        var allAvailable = repository.findAllAvailable(Instant.now());
        return allAvailable.stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public BookReservationDto update(Long id, BookReservationDto dto) {
        var reservation = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(RESERVATION_NOT_FOUND_EXCEPTION_MESSAGE.formatted(id)));
        mapper.update(reservation, dto);
        return mapper.toResponse(reservation);
    }

    @Override
    @Transactional
    public BookReservationCreationResponse create(BookReservationCreationRequest dto) {
        var reservation = mapper.toEntity(dto);
        reservation.setBorrowingTime(LocalDateTime.now());
        reservation.setReturnTime(LocalDateTime.now().plusMonths(1));
        reservation = repository.save(reservation);

        return mapper.toCreationResponse(reservation);
    }
}
