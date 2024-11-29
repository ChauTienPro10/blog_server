package chauduong.myapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name="user_tb")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng sequence hoặc auto-increment
    @Column(name = "ID") // Tên cột trong bảng
    private Long id;

    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
}
