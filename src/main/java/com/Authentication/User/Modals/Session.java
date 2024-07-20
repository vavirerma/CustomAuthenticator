package com.Authentication.User.Modals;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session extends BaseModal{
    private String token;
    private Date expiringAt;
    @ManyToOne
    private Users user;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
