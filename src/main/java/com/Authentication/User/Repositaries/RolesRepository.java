package com.Authentication.User.Repositaries;

import com.Authentication.User.Modals.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
    //List<Roles> findAllById(List<UUID> Id);
}
