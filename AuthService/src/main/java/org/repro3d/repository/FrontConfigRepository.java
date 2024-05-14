package org.repro3d.repository;

import org.repro3d.model.FrontConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FrontConfigRepository extends JpaRepository<FrontConfig, Long> {
    FrontConfig findByKey(String key);
}