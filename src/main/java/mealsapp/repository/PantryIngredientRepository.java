package mealsapp.repository;

import mealsapp.model.PantryIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {
}
