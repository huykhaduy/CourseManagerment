package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
