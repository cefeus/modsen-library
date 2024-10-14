package org.library.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.library.model.dto.GenreDto;
import org.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
@Tag(name = "Genres", description = "API for genres management")
public class GenreController {

    private final GenreService genreService;

    @Operation(summary = "Get genre by id", description = "Returns genre by provided id")
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @Operation(summary = "Get all genres", description = "Returns all genres")
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @Operation(summary = "Create new genre", description = "Creates new genre in table")
    @PostMapping
    public ResponseEntity<GenreDto> create(@RequestBody @Valid GenreDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.save(dto));
    }

    @Operation(summary = "Update genre by id", description = "Updates corresponding genre record by given id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreDto> update(@PathVariable Long id,
                                           @RequestBody @Valid GenreDto dto) {
        return ResponseEntity.ok(genreService.update(id, dto));
    }

    @Operation(summary = "Delete genre by id", description = "Deletes genre by given id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
