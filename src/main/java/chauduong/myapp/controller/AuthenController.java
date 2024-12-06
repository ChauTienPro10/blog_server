package chauduong.myapp.controller;

import chauduong.myapp.dto.request.LoginRequest;
import chauduong.myapp.dto.request.UserCreateRequest;
import chauduong.myapp.dto.response.ApiResponse;
import chauduong.myapp.dto.response.LoginResponse;
import chauduong.myapp.dto.response.UserCreateResponse;
import chauduong.myapp.entity.User;
import chauduong.myapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
public class AuthenController {
    @Autowired
    AuthService authService;

    @PostMapping("/new")
    public ApiResponse<UserCreateResponse> createUser(@RequestBody UserCreateRequest request){
        return authService.createUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody  LoginRequest request){
        return authService.login(request);
    }
}
