package com.practice.photogram.domain.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA query method



    @EntityGraph(attributePaths = {"images"}) //
    // 이거 페치조인인데, 만약 페이징이 필요하면 컬렉션에 대해서는 페치조인을 하면 안 된다.
    // batch를 사용하기때문에 지연로딩으로 불러와도 알아서 in으로 묶어서 불러와준다.
    Optional<User> findProfileById(Integer integer);


    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
//    Optional<User> findByUsername(String username);

//    @EntityGraph(attributePaths = {"profileImageUrl", ""})
    User findByUsername(String username);
}