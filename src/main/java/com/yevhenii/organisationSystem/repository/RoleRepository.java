package com.yevhenii.organisationSystem.repository;

import com.yevhenii.organisationSystem.entity.Role;
import com.yevhenii.organisationSystem.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}