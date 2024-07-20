package com.Authentication.User.Modals;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users extends BaseModal{
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Session> sessions;
    @ManyToMany(cascade = CascadeType.PERSIST)
    Set<Roles> roles;
}
