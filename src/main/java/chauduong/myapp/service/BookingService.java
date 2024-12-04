package chauduong.myapp.service;

import chauduong.myapp.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    BookRepository bookRepository;
}
