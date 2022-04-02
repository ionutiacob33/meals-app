package mealsapp.repository;

import mealsapp.model.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {
    Quantity findByAmount(Integer amount);
}
