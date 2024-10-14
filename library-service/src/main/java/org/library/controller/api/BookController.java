package org.library.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.library.model.dto.request.BookRequestDto;
import org.library.model.dto.response.BookResponseDto;
import org.library.model.dto.response.CreationBookResponseDto;
import org.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API for books management")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get book by id", description = "Returns book by provided id")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @Operation(summary = "Get book by ISBN", description = "Return book by provided ISBN")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDto> getByISBN(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }

    @Operation(summary = "Get all books", description = "Returns all books")
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @Operation(summary = "Create new book", description = "Creates new book in table")
    @PostMapping
    public ResponseEntity<CreationBookResponseDto> create(@RequestBody @Valid BookRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(dto));
    }

    @Operation(summary = "Update book by id", description = "Updates corresponding book record by given id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDto> update(@PathVariable Long id,
                                                  @RequestBody @Valid BookRequestDto dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @Operation(summary = "Delete book", description = "Delete book record by given id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
