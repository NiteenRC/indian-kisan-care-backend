package com.nc.med.repo;

import com.nc.med.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsernameContainingIgnoreCase(String username);

    Boolean existsByEmail(String email);
}
