package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.roles where u.email = :email")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    Optional<User> findById(Long id);

}
