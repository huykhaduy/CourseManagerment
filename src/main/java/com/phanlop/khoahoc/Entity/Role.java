package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = {"listUsers"})
@EqualsAndHashCode(exclude = {"listUsers"})
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer roleId;
    @NotNull(message = "Tên vai trò không được rỗng")
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name="role_id"),
                inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> listUsers = new HashSet<>();
}
