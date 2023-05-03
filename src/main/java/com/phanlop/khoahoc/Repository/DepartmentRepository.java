package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
