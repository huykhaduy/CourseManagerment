package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Role;
import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);

    @Query("SELECT u FROM User u JOIN u.listRoles r WHERE r = :role")
    List<User> findUserByRole(Role role);
}
