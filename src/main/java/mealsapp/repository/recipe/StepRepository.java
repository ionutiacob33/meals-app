package mealsapp.repository.recipe;

import mealsapp.model.recipe.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Long> {
    Step findByCountAndDescription(int count, String description);
    List<Step> findByRecipeId(Long recipeId);
    void deleteAllByRecipeId(Long recipeId);
}
