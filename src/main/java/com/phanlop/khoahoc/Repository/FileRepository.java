package com.phanlop.khoahoc.Repository;

import com.phanlop.khoahoc.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}
