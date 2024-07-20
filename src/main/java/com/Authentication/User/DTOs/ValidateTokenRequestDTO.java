package com.Authentication.User.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ValidateTokenRequestDTO {
    private UUID userId;
    private String token;

}
