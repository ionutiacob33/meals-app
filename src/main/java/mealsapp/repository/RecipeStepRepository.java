package mealsapp.repository;

import mealsapp.model.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    List<RecipeStep> findByRecipeId(Long id);
    void deleteAllByRecipeId(Long id);
}
