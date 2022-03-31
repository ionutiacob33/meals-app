package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.Ingredient;
import mealsapp.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient addIngredient(String name) {
        Ingredient ingredient = ingredientRepository.findByName(name);
        if (ingredient == null) {
            ingredient = new Ingredient();
            ingredient.setName(name);
            ingredientRepository.save(ingredient);
        }
        return ingredient;
    }

}
