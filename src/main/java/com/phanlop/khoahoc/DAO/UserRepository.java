package com.phanlop.khoahoc.DAO;

import com.phanlop.khoahoc.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users-api")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
