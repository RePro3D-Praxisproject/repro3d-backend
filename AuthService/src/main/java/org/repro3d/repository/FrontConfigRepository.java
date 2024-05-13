package org.repro3d.repository;

import org.repro3d.model.FrontConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrontConfigRepository extends JpaRepository<FrontConfig, Long> {
    FrontConfig findByKey(String key);
}