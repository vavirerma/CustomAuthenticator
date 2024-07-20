package com.Authentication.User.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LogoutRequestDTO {
    private String token;
    private UUID userId;
}
