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

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeDto getRecipe(Long id) {
        RecipeDto detailedRecipe = new RecipeDto();
        Recipe recipe = recipeRepository.getById(id);
        detailedRecipe.setTitle(recipe.getTitle());
        detailedRecipe.setDescription(recipe.getDescription());
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByRecipeId(id);
        List<RecipeIngredientDto> recipeIngredientsDto = recipeIngredients.stream()
                .map(this::mapToRecipeIngredientDto)
                .toList();
        detailedRecipe.setRecipeIngredients(recipeIngredientsDto);
        return detailedRecipe;
    }

    private List<RecipeIngredient> mapRecipeIngredientsDto(List<RecipeIngredientDto> recipeIngredients, Recipe recipe) {
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

    private RecipeIngredientDto mapToRecipeIngredientDto(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();

        recipeIngredientDto.setIngredient(recipeIngredient.getIngredient().getName());
        recipeIngredientDto.setUnit(recipeIngredient.getUnit().getName());
        recipeIngredientDto.setQuantity(recipeIngredient.getQuantity().getAmount());

        return recipeIngredientDto;
    }

}
