package com.practice.photogram.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA query method



    @EntityGraph(attributePaths = {"images"})
    Optional<User> findProfileById(Integer integer);


    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);

//    @EntityGraph(attributePaths = {"profileImageUrl", ""})
//    User findByUsername(String username);
}