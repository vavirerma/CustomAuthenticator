package com.Authentication.User.Repositaries;

import com.Authentication.User.Modals.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Users findByEmailAndPassword(String email,String password);

    Optional<Users> findByEmail(String email);
}
