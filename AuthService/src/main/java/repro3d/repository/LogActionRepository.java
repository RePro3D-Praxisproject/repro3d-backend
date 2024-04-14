package repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repro3d.model.LogAction;

@Repository
public interface LogActionRepository extends JpaRepository<LogAction, Long> {
}
