package chauduong.myapp.dto.request.booking;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewBookingRequest {
    private String startTime;
    private String endTime;
    private Long courtId;
}
