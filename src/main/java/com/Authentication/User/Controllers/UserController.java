package com.Authentication.User.Controllers;

import com.Authentication.User.DTOs.CreateUserRoleDTO;
import com.Authentication.User.DTOs.UserDTO;
import com.Authentication.User.Modals.Users;
import com.Authentication.User.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable("id") UUID userId) {
        UserDTO userDto = userService.getUserDetails(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDTO> setUserRoles(@PathVariable("id") UUID userId, @RequestBody CreateUserRoleDTO request) {

        UserDTO userDto = userService.setUserRoles(userId, request.getRoleIds());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
