package mealsapp.service.recipe.ingredient;

import lombok.AllArgsConstructor;
import mealsapp.model.recipe.ingredient.Ingredient;
import mealsapp.repository.recipe.ingredient.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public void add(List<Ingredient> ingredients) {
        ingredientRepository.saveAll(ingredients);
    }

    public List<Ingredient> getByRecipeId(Long id) {
        return ingredientRepository.findByRecipeId(id);
    }

    public void deleteByRecipeId(Long id) {
        ingredientRepository.deleteAllByRecipeId(id);
    }

}
