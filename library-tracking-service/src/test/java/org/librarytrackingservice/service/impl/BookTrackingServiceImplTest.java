package org.librarytrackingservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.librarytrackingservice.mapper.BookReservationMapper;
import org.librarytrackingservice.model.BookReservation;
import org.librarytrackingservice.model.dto.BookReservationCreationRequest;
import org.librarytrackingservice.model.dto.BookReservationDto;
import org.librarytrackingservice.repository.BookReservationRepository;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookTrackingServiceImplTest {

    @InjectMocks
    BookTrackingServiceImpl service;

    @Mock
    private  BookReservationRepository repository;

    @Spy
    private BookReservationMapper mapper = Mappers.getMapper(BookReservationMapper.class);

    @Captor
    ArgumentCaptor<Instant> instantCaptor;


    @Test
    void checkFindAll_shouldReturnCorrectMappedList() {
        LocalDateTime borrowingTime = LocalDateTime.now();
        LocalDateTime returnTime = borrowingTime.plusMonths(1);
        LocalDateTime borrowingTime2 = LocalDateTime.now().minusMonths(2);
        LocalDateTime returnTime2 = borrowingTime2.plusMonths(1);
        List<BookReservation> reservations = List.of(
                BookReservation.builder().id(1L).bookId(1L).borrowingTime(borrowingTime).returnTime(returnTime).build(),
                BookReservation.builder().id(2L).bookId(2L).borrowingTime(borrowingTime2).returnTime(returnTime2).build()
        );

        doReturn(reservations).when(repository).findAll();

        List<BookReservationDto> all = service.findAll();
        IntStream.range(0, all.size())
                .forEach(x -> {
                    Long actualBookId = all.get(x).bookId();
                    Long expectedBookId = reservations.get(x).getBookId();

                    LocalDateTime actualBorrowingTime = all.get(x).borrowingTime();
                    LocalDateTime expectedBorrowingTime = reservations.get(x).getBorrowingTime();

                    LocalDateTime actualReturnTime = all.get(x).returnTime();
                    LocalDateTime expectedReturnTime = reservations.get(x).getReturnTime();

                    assertThat(actualBookId).isEqualTo(expectedBookId);
                    assertThat(actualBorrowingTime).isEqualTo(expectedBorrowingTime);
                    assertThat(actualReturnTime).isEqualTo(expectedReturnTime);
                });
    }

    @Test
    void checkFindAllAvailable_shouldReturnCorrectMappedListAndSearchByCurrentInstant() {
        LocalDateTime borrowingTime = LocalDateTime.now().minusMonths(3);
        LocalDateTime returnTime = borrowingTime.plusMonths(1);
        LocalDateTime borrowingTime2 = LocalDateTime.now().minusMonths(4);
        LocalDateTime returnTime2 = borrowingTime2.plusMonths(1);
        Instant now = Instant.now();
        List<BookReservation> reservations = List.of(
                BookReservation.builder().id(1L).bookId(1L).borrowingTime(borrowingTime).returnTime(returnTime).build(),
                BookReservation.builder().id(2L).bookId(2L).borrowingTime(borrowingTime2).returnTime(returnTime2).build()
        );

        doReturn(reservations).when(repository).findAllAvailable(instantCaptor.capture());

        List<BookReservationDto> all = service.findAllAvailable();
        Instant actual = instantCaptor.getValue();
        IntStream.range(0, all.size())
                .forEach(x -> {
                    Long actualBookId = all.get(x).bookId();
                    Long expectedBookId = reservations.get(x).getBookId();

                    LocalDateTime actualBorrowingTime = all.get(x).borrowingTime();
                    LocalDateTime expectedBorrowingTime = reservations.get(x).getBorrowingTime();

                    LocalDateTime actualReturnTime = all.get(x).returnTime();
                    LocalDateTime expectedReturnTime = reservations.get(x).getReturnTime();

                    assertThat(actualBookId).isEqualTo(expectedBookId);
                    assertThat(actualBorrowingTime).isEqualTo(expectedBorrowingTime);
                    assertThat(actualReturnTime).isEqualTo(expectedReturnTime);
                });
        assertThat(actual).isCloseTo(now, within(1, ChronoUnit.SECONDS));
    }

    @Test
    void checkFindAllEmpty_shouldReturnEmpty(){
        doReturn(List.of()).when(repository).findAll();

        List<BookReservationDto> all = service.findAll();

        assertThat(all).isEmpty();
    }

    @Test
    void update_shouldUpdateTargetEntityFields() {
        LocalDateTime time = LocalDateTime.now();
        Long id = 1L;
        BookReservationDto reservationDto = new BookReservationDto(3L,time, null);
        BookReservation reservation = BookReservation.builder().id(id).bookId(2L).returnTime(time).borrowingTime(time.plusMonths(1)).build();

        doReturn(Optional.of(reservation)).when(repository).findById(id);

        BookReservationDto actual = service.update(id, reservationDto);
        assertThat(actual.bookId()).isEqualTo(reservation.getBookId());
        assertThat(actual.borrowingTime()).isEqualTo(reservation.getBorrowingTime());
        assertThat(actual.returnTime()).isEqualTo(reservation.getReturnTime());

    }

    @Test
    void create_shouldSaveEntityWithoutIdByOneRepoSave() {
        BookReservationCreationRequest request = new BookReservationCreationRequest(1L);

        service.create(request);

        verify(repository, times(1)).save(argThat(x -> Objects.isNull(x.getId())));
    }
}