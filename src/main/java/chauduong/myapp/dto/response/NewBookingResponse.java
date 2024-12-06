package chauduong.myapp.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NewBookingResponse {
    private String startTime;
    private String endTime;
    private boolean state;
    private Long courtId;
    private String user;

}
