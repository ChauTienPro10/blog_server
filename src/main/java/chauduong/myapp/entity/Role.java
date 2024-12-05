package chauduong.myapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ROLE_TB")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng sequence hoặc auto-increment
    @Column(name = "ID") // Tên cột trong bảng
    private Long id;
    private String role;



}
