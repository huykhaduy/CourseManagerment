package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
