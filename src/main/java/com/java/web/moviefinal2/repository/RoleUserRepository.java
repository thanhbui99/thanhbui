package com.java.web.moviefinal2.repository;

import com.java.web.moviefinal2.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<UserRole,Long> {
}
