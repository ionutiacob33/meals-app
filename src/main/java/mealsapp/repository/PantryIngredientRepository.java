package mealsapp.repository;

import mealsapp.model.ingredient.Name;
import mealsapp.model.PantryIngredient;
import mealsapp.model.ingredient.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {
    List<PantryIngredient> findByUserId(Long userId);
    PantryIngredient findByNameAndUnit(Name name, Unit unit);
}
