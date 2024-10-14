package org.library.mapper;

import org.library.model.Author;
import org.library.model.dto.AuthorDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AuthorMapper {

    AuthorDto toResponse(Author author);

    Author toAuthor(AuthorDto dto);

    Author update(@MappingTarget Author author, AuthorDto dto);
}
