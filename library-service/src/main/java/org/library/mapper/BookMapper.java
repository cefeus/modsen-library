package org.library.mapper;

import org.library.model.Book;
import org.library.model.dto.request.BookRequestDto;
import org.library.model.dto.response.BookResponseDto;
import org.library.model.dto.response.CreationBookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface BookMapper {

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    BookResponseDto toResponse(Book book);

    CreationBookResponseDto toCreationResponse(Book book);


    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Book toBook(BookRequestDto dto);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Book update(@MappingTarget Book book, BookRequestDto dto);
}
