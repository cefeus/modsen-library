package org.librarytrackingservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.librarytrackingservice.model.dto.BookReservationCreationRequest;
import org.librarytrackingservice.model.dto.BookReservationCreationResponse;
import org.librarytrackingservice.model.dto.BookReservationDto;
import org.librarytrackingservice.service.BookTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tracking")
@RequiredArgsConstructor
@Tag(name = "BooksTracker", description = "API for books tracking")
public class BookTrackingController {
    private final BookTrackingService service;

    @Operation(summary = "Create book reservation", description = "Creates book reservation by provided book id")
    @PostMapping
    public ResponseEntity<BookReservationCreationResponse> create(@RequestBody @Valid BookReservationCreationRequest dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get all book reservations", description = "Returns all books reservation info")
    @GetMapping
    public ResponseEntity<List<BookReservationDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get all available books reservations", description = "Returns all books reservations with expired return date or without return date")
    @GetMapping("/available")
    public ResponseEntity<List<BookReservationDto>> getAllAvailable(){
        return ResponseEntity.ok(service.findAllAvailable());
    }

    @Operation(summary = "Update existing book reservation", description = "Updates existing book reservation with new data")
    @PutMapping("{id}")
    public ResponseEntity<BookReservationDto> update(@PathVariable Long id, @RequestBody BookReservationDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }
}
