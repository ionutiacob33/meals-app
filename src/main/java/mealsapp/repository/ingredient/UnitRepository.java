package mealsapp.repository.ingredient;

import mealsapp.model.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByUnit(String unit);
}
