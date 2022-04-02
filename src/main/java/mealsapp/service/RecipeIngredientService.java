package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.RecipeIngredient;
import mealsapp.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public void add(List<RecipeIngredient> recipeIngredients) {
        recipeIngredientRepository.saveAll(recipeIngredients);
    }

    public List<RecipeIngredient> getByRecipeId(Long id) {
        return recipeIngredientRepository.findByRecipeId(id);
    }

    public void deleteByRecipeId(Long id) {
        recipeIngredientRepository.deleteAllByRecipeId(id);
    }

}
