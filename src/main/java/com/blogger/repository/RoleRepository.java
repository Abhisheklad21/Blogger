package com.blogger.repository;

import com.blogger.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

      @Query("SELECT r FROM Role r WHERE r.roleName = :roleName")
      Optional<Role> findByName(@Param("roleName") String roleName);

}
