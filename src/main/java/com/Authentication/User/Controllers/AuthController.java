package com.Authentication.User.Controllers;

import com.Authentication.User.DTOs.*;
import com.Authentication.User.Modals.SessionStatus;
import com.Authentication.User.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO request) {
        return authService.login(request.getEmail(), request.getPassword());
//        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDTO request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUPRequestDTO request) {
        UserDTO userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    @GetMapping("/signup")
//    public String signUp() {
////        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
//        return "hello";
//    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDTO request) {
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());

        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }
}
