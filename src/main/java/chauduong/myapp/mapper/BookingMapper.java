package chauduong.myapp.mapper;

import chauduong.myapp.dto.response.NewBookingResponse;
import chauduong.myapp.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface BookingMapper {
    BookingMapper INSTANCE= Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "startTime",source ="book_start" )
    @Mapping(target = "endTime",source ="book_end" )
    @Mapping(target = "user",source ="user.name" )
    @Mapping(target = "state",source ="state" )
    NewBookingResponse toNewBookingResponse(Booking booking);

}
