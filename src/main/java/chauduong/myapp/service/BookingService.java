package chauduong.myapp.service;

import chauduong.myapp.configuration.JwtProvider;
import chauduong.myapp.dto.request.booking.NewBookingRequest;
import chauduong.myapp.dto.response.ApiResponse;
import chauduong.myapp.dto.response.NewBookingResponse;
import chauduong.myapp.entity.Booking;
import chauduong.myapp.entity.User;
import chauduong.myapp.mapper.BookingMapper;
import chauduong.myapp.repository.AccountRepository;
import chauduong.myapp.repository.BookingRepository;
import chauduong.myapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingMapper bookingMapper;
    @Autowired
    AccountRepository accountRepository;

    public ApiResponse<NewBookingResponse> booking(NewBookingRequest request,String jwt){
        try{
            // xu ly user
            Claims claims=jwtProvider.getClaimsFromToken(jwt);
            String username= claims.getSubject();
            User user= accountRepository.findByUsername(username).get().getUser();


            // xu ly time
            LocalDateTime now=LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH-mm");
            LocalDateTime startTime = LocalDateTime.parse(request.getStartTime());
            List<Booking> bookls=bookingRepository.findBookingsByDate(String.valueOf(startTime.toLocalDate()));
            System.out.println(bookls.size());
            if(startTime.isBefore(now)){
                return ApiResponse.<NewBookingResponse>builder()
                        .code(1004)
                        .message("Invalid time")
                        .build();
            }
            LocalDateTime endTime = LocalDateTime.parse(request.getEndTime());
            Booking newBook= Booking.builder()
                    .book_start(startTime)
                    .book_end(endTime)
                    .state(true)
                    .user(user)
                    .build();
            bookingRepository.save(newBook);
            user.getBookings().add(newBook);
            userRepository.save(user);
            NewBookingResponse rp=bookingMapper.toNewBookingResponse(newBook);

            return ApiResponse.<NewBookingResponse>builder()
                    .code(1000)
                    .message("You have successfully booked an appointment!")
                    .result(rp)
                    .build();
        }
        catch (Exception e){
            return ApiResponse.<NewBookingResponse>builder()
                    .code(500)
                    .message("")
                    .build();
        }

    }
}
