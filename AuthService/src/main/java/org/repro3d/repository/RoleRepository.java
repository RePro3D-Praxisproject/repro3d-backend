package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
