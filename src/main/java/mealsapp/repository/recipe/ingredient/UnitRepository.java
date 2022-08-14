package mealsapp.repository.recipe.ingredient;

import mealsapp.model.recipe.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByUnit(String unit);
}
