package mealsapp.repository;

import mealsapp.model.PantryIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryIngredientRepository extends JpaRepository<PantryIngredient, Long> {
    List<PantryIngredient> findByUserId(Long userId);
}
