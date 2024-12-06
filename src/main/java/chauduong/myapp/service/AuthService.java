package chauduong.myapp.service;

import chauduong.myapp.configuration.JwtProvider;
import chauduong.myapp.dto.request.LoginRequest;
import chauduong.myapp.dto.request.UserCreateRequest;
import chauduong.myapp.dto.response.ApiResponse;
import chauduong.myapp.dto.response.LoginResponse;
import chauduong.myapp.dto.response.UserCreateResponse;
import chauduong.myapp.entity.Account;
import chauduong.myapp.entity.Role;
import chauduong.myapp.entity.User;
import chauduong.myapp.mapper.UserMapper;
import chauduong.myapp.repository.AccountRepository;
import chauduong.myapp.repository.RoleRepository;
import chauduong.myapp.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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

    @Autowired
    UserMapper userMapper;

    public ApiResponse<UserCreateResponse> createUser(UserCreateRequest request) {
        try {
            // Kiểm tra username đã tồn tại
            if (accountRepository.existsByUsername(request.getUsername())) {
                return ApiResponse.<UserCreateResponse>builder()
                        .code(1001)
                        .message("Username already exists!")
                        .build();
            }
            if(userRepository.existsByPhone(request.getPhone())){
                return  ApiResponse.<UserCreateResponse>builder()
                        .code(1003)
                        .message("Phone number already exist")
                        .build();
            }

            // Lấy danh sách roles từ request
            List<String> roleNames = Arrays.asList(request.getRoles());

            // Tối ưu xử lý roles
            Map<String, Role> existingRoles = roleRepository.findAll().stream()
                    .collect(Collectors.toMap(Role::getRole, Function.identity()));

            Set<Role> roles = roleNames.stream()
                    .map(roleName -> existingRoles.computeIfAbsent(roleName, name -> {
                        Role newRole = Role.builder().role(name).build();
                        roleRepository.save(newRole);
                        return newRole;
                    }))
                    .collect(Collectors.toSet());

            // Tạo và lưu Account
            Account ac = Account.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(roles)
                    .build();
            accountRepository.save(ac);
            // Tạo và lưu User
            User us = User.builder()
                    .name(request.getName())
                    .phone(request.getPhone())
                    .account(ac)
                    .build();
            userRepository.save(us);
            ac.setUser(us);
            accountRepository.save(ac);

            UserCreateResponse rp = userMapper.toUserCreateResponse(us);
            return ApiResponse.<UserCreateResponse>builder()
                    .code(1000)
                    .message("Create user successfully!")
                    .result(rp)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserCreateResponse>builder()
                    .code(1002)
                    .message("Failed to create user: " + e.getMessage())
                    .build();
        }
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
