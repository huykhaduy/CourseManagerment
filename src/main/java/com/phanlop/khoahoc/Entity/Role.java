package com.phanlop.khoahoc.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer roleId;
    private String roleName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinTable(name = "user_role",
                joinColumns = @JoinColumn(name="role_id"),
                inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> listUsers = new HashSet<>();
}
