package com.Authentication.User.Controllers;

import com.Authentication.User.DTOs.CreateRoleDTO;
import com.Authentication.User.Modals.Roles;
import com.Authentication.User.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }
    @PostMapping
    public ResponseEntity<Roles> createRole(CreateRoleDTO request) {
        Roles role = roleService.createRole(request.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

}
