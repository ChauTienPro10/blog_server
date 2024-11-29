package chauduong.myapp.service;

import chauduong.myapp.configuration.JwtProvider;
import chauduong.myapp.dto.request.LoginRequest;
import chauduong.myapp.dto.request.UserCreateRequest;
import chauduong.myapp.dto.response.LoginResponse;
import chauduong.myapp.entity.Account;
import chauduong.myapp.entity.Role;
import chauduong.myapp.entity.User;
import chauduong.myapp.repository.AccountRepository;
import chauduong.myapp.repository.RoleRepository;
import chauduong.myapp.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtProvider jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreateRequest request) {
        List<String> roleNames = Arrays.asList(request.getRoles());  // Chuyển mảng roles thành List

        // Kiểm tra và lưu các Role nếu chưa tồn tại
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByRole(roleName).orElseGet(() -> {
                Role newRole = Role.builder().role(roleName).build();
                return roleRepository.save(newRole);  // Lưu Role nếu chưa tồn tại
            });
            roles.add(role);
        }

        // Tạo và lưu Account
        Account ac = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))  // Mã hóa mật khẩu
                .roles(roles)  // Gán các role đã được lưu
                .build();
        accountRepository.save(ac);

        // Tạo và lưu User
        User us = User.builder()
                .name(request.getName())
                .account(ac)  // Gán Account cho User
                .build();

        return userRepository.save(us);  // Lưu User vào cơ sở dữ liệu
    }

    public String  login(LoginRequest loginRequest){

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được trống.");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );


        // Nếu xác thực thành công, tạo JWT token
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authentication); // Sử dụng phương thức generateToken để tạo JWT token
        } else {
            throw new RuntimeException("Đăng nhập thất bại: Email hoặc mật khẩu không đúng");
        }
    }


}
