package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
