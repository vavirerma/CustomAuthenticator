package com.Authentication.User.DTOs;

import com.Authentication.User.Modals.Roles;
import com.Authentication.User.Modals.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String email;
    private Set<Roles> roles = new HashSet<>();

    public static UserDTO from(Users user) {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(user.getEmail());
//        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
