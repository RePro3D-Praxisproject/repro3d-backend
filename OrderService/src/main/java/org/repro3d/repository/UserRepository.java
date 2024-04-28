package org.repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.repro3d.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
