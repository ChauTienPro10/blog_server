package chauduong.myapp.repository;

import chauduong.myapp.entity.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query(value = "SELECT * FROM BOOKING_TB b WHERE TRUNC(b.book_start) = TO_DATE(:date, 'YYYY-MM-DD')", nativeQuery = true)
    List<Booking> findBookingsByDate(@Param("date") String date);
}
