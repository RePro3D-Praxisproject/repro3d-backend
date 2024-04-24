package repro3d.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import repro3d.model.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {


}
