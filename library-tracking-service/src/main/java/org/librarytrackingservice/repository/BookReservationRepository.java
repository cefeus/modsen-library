package org.librarytrackingservice.repository;

import org.librarytrackingservice.model.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    @Query(value = "SELECT * FROM book_reservations br " +
            "WHERE borrowing_time = NULL " +
            "OR return_time < :time",
            nativeQuery = true)
    List<BookReservation> findAllAvailable(@Param("time")Instant returnTime);
}
