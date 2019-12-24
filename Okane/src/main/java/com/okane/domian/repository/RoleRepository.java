package com.okane.domian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okane.domain.entity.UserRole;
@Repository
public interface RoleRepository extends JpaRepository<UserRole, Integer> {

}
