package chauduong.myapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ACCOUNT_TB")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng sequence hoặc auto-increment
    @Column(name = "ID") // Tên cột trong bảng
    private Long id;

    private String username;
    private String password;
    @OneToOne
    @JoinColumn(name = "user_id")  // Tên cột khóa ngoại trong bảng Account
    private User user;  // Mối quan hệ với User


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "account_id"), // Khóa ngoại từ bảng account
            inverseJoinColumns = @JoinColumn(name = "role_id") // Khóa ngoại từ bảng role
    )
    private Set<Role> roles = new HashSet<>();

}
