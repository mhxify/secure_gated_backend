package com.smartgated.platform.infrastructure.repository.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgated.platform.domain.enums.user.UserRole;
import com.smartgated.platform.domain.model.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRole(UserRole role);

    List<User> findByRole(UserRole role);

}
