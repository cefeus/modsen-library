package org.authservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.authservice.mapper.UserMapper;
import org.authservice.model.User;
import org.authservice.model.dto.UserCredentialsDto;
import org.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private  UserRepository repository;
    @Spy
    private  UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void signUp_shouldInvokeRepoOneTimeAndSaveEntityWithoutId() {
        UserCredentialsDto dto = new UserCredentialsDto();
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setEmail("email@gmail.com");

        service.signUp(dto);
        verify(repository, times(1)).save(argThat(x -> Objects.isNull(x.getId())));
    }

    @Test
    void getByUsername_shouldReturnUserWithFilledFieldsAndUsernameFromRequest() {
        String name = "Username";
        Optional<User> optionalUser = Optional.of(User.builder().id(1L).username(name).password("Password").email("email@gmail.com").build());

        doReturn(optionalUser).when(repository).findByUsername(name);

        User actual = service.getByUsername(name);

        assertThat(actual.getUsername()).isEqualTo(optionalUser.get().getUsername());
        assertThat(actual.getPassword()).isNotBlank();
        assertThat(actual.getEmail()).isNotBlank();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void getByUsername_shouldThrowEntityNotFound() {
        String name = "name";
        doReturn(Optional.empty()).when(repository).findByUsername(name);

        assertThatThrownBy(() -> service.getByUsername(name)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findById_shouldReturnCorrectUserDto() {
        Long id = 1L;
        Optional<User> optionalUser = Optional.of(User.builder().id(id).username("Username").password("Password").email("email@gmail.com").build());

        doReturn(optionalUser).when(repository).findById(id);

        UserCredentialsDto dto = service.findById(id);

        assertThat(dto.getUsername()).isEqualTo(optionalUser.get().getUsername());
        assertThat(dto.getPassword()).isEqualTo(optionalUser.get().getPassword());
        assertThat(dto.getEmail()).isEqualTo(optionalUser.get().getEmail());
    }

    @Test
    void findById_shouldThrowEntityNotFound() {
        doReturn(Optional.empty()).when(repository).findById(1L);

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void update_shouldReturnUpdatedUser() {
        String name = "name";
        Long id = 1L;
        UserCredentialsDto dto = new UserCredentialsDto();
        dto.setUsername("New name");
        dto.setEmail("newemail@gmail.com");
        User user = User.builder().id(1L).username(name).password("pass").email("email@gmail.com").build();

        doReturn(Optional.of(user)).when(repository).findById(id);

        UserCredentialsDto actual = service.update(dto, id);
        assertThat(actual.getUsername()).isEqualTo(dto.getUsername());
        assertThat(actual.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    void delete_shouldInvokeDeleteOnce() {
        Long id = 1L;
        User user = new User();
        doReturn(Optional.of(user)).when(repository).findById(id);

        service.delete(1L);

        verify(repository, times(1)).delete(user);
    }

    @Test
    void delete_shouldThrowEntityNotFound() {
        Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
    }
}