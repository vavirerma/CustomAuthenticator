package com.Authentication.User.Services;

import com.Authentication.User.DTOs.UserDTO;
import com.Authentication.User.Modals.Roles;
import com.Authentication.User.Modals.Users;
import com.Authentication.User.Repositaries.RolesRepository;
import com.Authentication.User.Repositaries.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository usersRepository;
    private RolesRepository rolesRepository;
    public UserService(UserRepository usersRepository,RolesRepository rolesRepository){
        this.usersRepository=usersRepository;
        this.rolesRepository=rolesRepository;
    }
    public UserDTO getUserDetails(UUID userId) {
        return new UserDTO();
    }
    public UserDTO setUserRoles(UUID userId, List<UUID> roleIds) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        List<Roles> roles = rolesRepository.findAllById(roleIds);
        if (userOptional.isEmpty()) {
            return null;
        }
        Users user = userOptional.get();
//        user.setRoles(Set.copyOf(roles));
        Users savedUser = usersRepository.save(user);
        return UserDTO.from(savedUser);
    }
}
