package org.authservice.mapper;

import org.authservice.model.User;
import org.authservice.model.dto.SignUpResponse;
import org.authservice.model.dto.UserCredentialsDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {

    SignUpResponse toResponse(User user);

    UserCredentialsDto toDto(User user);

    User toEntity(UserCredentialsDto request);

    User update(@MappingTarget User user, UserCredentialsDto dto);
}
