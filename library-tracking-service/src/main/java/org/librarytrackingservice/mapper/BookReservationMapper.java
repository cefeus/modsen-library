package org.librarytrackingservice.mapper;

import org.librarytrackingservice.model.BookReservation;
import org.librarytrackingservice.model.dto.BookReservationCreationRequest;
import org.librarytrackingservice.model.dto.BookReservationCreationResponse;
import org.librarytrackingservice.model.dto.BookReservationDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface BookReservationMapper {
    BookReservation toEntity(BookReservationCreationRequest dto);
    BookReservationDto toResponse(BookReservation bookReservation);
    BookReservationCreationResponse toCreationResponse(BookReservation reservation);
    BookReservation update(@MappingTarget BookReservation reservation, BookReservationDto dto);
}
