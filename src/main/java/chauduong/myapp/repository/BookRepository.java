package chauduong.myapp.repository;

import chauduong.myapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Booking,Long> {

}
