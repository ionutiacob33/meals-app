package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.model.*;
import mealsapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;
    private final QuantityRepository quantityRepository;

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());

        recipeRepository.save(recipe);
        List<RecipeIngredient> recipeIngredients = mapRecipeIngredientsDto(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientRepository.saveAll(recipeIngredients);

        return recipe;
    }

    private List<RecipeIngredient> mapRecipeIngredientsDto(ArrayList<RecipeIngredientDto> recipeIngredients, Recipe recipe) {
        return recipeIngredients.stream()
                .map(recipeIngredientDto -> mapToRecipeIngredient(recipeIngredientDto, recipe))
                .collect(Collectors.toList());
    }

    private RecipeIngredient mapToRecipeIngredient(RecipeIngredientDto recipeIngredientDto, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);

        Ingredient ingredient = new Ingredient();
        ingredient.setName(recipeIngredientDto.getIngredient());
        ingredientRepository.save(ingredient);
        recipeIngredient.setIngredient(ingredient);

        Unit unit = new Unit();
        unit.setName(recipeIngredientDto.getUnit());
        unitRepository.save(unit);
        recipeIngredient.setUnit(unit);

        Quantity quantity = new Quantity();
        quantity.setAmount(recipeIngredientDto.getQuantity());
        quantityRepository.save(quantity);
        recipeIngredient.setQuantity(quantity);

        return recipeIngredient;
    }

}
