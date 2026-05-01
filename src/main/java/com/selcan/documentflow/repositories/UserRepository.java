package com.selcan.documentflow.repositories;

import com.selcan.documentflow.entity.User;
import com.selcan.documentflow.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findFirstByRole(Role role);

    List<User> findByRole(Role role);

}
