package com.Authentication.User.Services;

import com.Authentication.User.Modals.Roles;
import com.Authentication.User.Repositaries.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RolesRepository roleRepository;
    public RoleService(RolesRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Roles createRole(String name) {
        Roles role = new Roles();
        role.setRole(name);
        return roleRepository.save(role);
    }
}
