package com.dunght.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dunght.jwt.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
