package chauduong.myapp.dto.request;

import chauduong.myapp.entity.Role;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    private String name;
    private String username;
    private String password;
    private String[] roles;
}
