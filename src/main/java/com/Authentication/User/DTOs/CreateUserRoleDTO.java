package com.Authentication.User.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateUserRoleDTO {
    private List<UUID> roleIds;
}

