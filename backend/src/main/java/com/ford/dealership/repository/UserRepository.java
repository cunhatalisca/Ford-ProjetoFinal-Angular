package com.ford.dealership.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ford.dealership.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
