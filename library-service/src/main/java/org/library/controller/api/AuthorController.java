package org.library.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.library.model.dto.AuthorDto;
import org.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "API for authors management")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Get author by id", description = "Returns author by given id")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @Operation(summary = "Get author by name", description = "Returns author by given name")
    @GetMapping("/{name}")
    public ResponseEntity<AuthorDto> getByName(@PathVariable String name) {
        return ResponseEntity.ok(authorService.findByName(name));
    }

    @Operation(summary = "Get all authors", description = "Returns all authors")
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @Operation(summary = "Create author", description = "Creates new author in table")
    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody @Valid AuthorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(dto));
    }

    @Operation(summary = "Update author by id", description = "Updates corresponding author record by given id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id,
                                            @RequestBody @Valid AuthorDto dto) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @Operation(summary = "Delete author", description = "Delete author record by given id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
