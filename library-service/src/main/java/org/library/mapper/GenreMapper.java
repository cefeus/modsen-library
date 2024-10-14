package org.library.mapper;

import org.library.model.Genre;
import org.library.model.dto.GenreDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface GenreMapper {

    GenreDto toResponse(Genre genre);

    Genre toGenre(GenreDto dto);

    Genre update(@MappingTarget Genre genre, GenreDto dto);
}
