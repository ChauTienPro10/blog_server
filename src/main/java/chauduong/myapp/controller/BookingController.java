package chauduong.myapp.controller;

import chauduong.myapp.dto.request.booking.NewBookingRequest;
import chauduong.myapp.dto.response.ApiResponse;
import chauduong.myapp.dto.response.NewBookingResponse;
import chauduong.myapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/newbooking")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<NewBookingResponse> newBooking(@RequestBody NewBookingRequest request,
            @RequestHeader("Authorization") String authorizationHeader) {
        try{
            String jwt = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : authorizationHeader;
            return bookingService.booking(request,jwt);
        }catch (Exception e){
            System.out.println(e.toString());
            return null;
        }

    }
}
