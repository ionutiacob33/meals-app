package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.model.*;
import mealsapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final QuantityService quantityService;

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());

        recipeRepository.save(recipe);
        List<RecipeIngredient> recipeIngredients = mapRecipeIngredientsDto(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

        return recipe;
    }

    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeId);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipeRepository.save(recipe);
        recipeIngredientService.deleteByRecipeId(recipeId);

        List<RecipeIngredient> recipeIngredients = mapRecipeIngredientsDto(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

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
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByRecipeId(id);
        List<RecipeIngredientDto> recipeIngredientsDto = recipeIngredients.stream()
                .map(this::mapRecipeIngredientDto)
                .toList();
        detailedRecipe.setRecipeIngredients(recipeIngredientsDto);
        return detailedRecipe;
    }

    public boolean deleteRecipe(Long id) {
        recipeIngredientService.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return true;
    }

    private List<RecipeIngredient> mapRecipeIngredientsDto(List<RecipeIngredientDto> recipeIngredientDtos, Recipe recipe) {
        return recipeIngredientDtos.stream()
                .map(recipeIngredientDto -> mapToRecipeIngredient(recipeIngredientDto, recipe))
                .collect(Collectors.toList());
    }

    private RecipeIngredient mapToRecipeIngredient(RecipeIngredientDto recipeIngredientDto, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);

        Ingredient ingredient = ingredientService.addIngredient(recipeIngredientDto.getIngredient());
        recipeIngredient.setIngredient(ingredient);

        Unit unit = unitService.addUnit(recipeIngredientDto.getUnit());
        recipeIngredient.setUnit(unit);

        Quantity quantity = quantityService.addQuantity(recipeIngredientDto.getQuantity());
        recipeIngredient.setQuantity(quantity);

        return recipeIngredient;
    }

    private RecipeIngredientDto mapRecipeIngredientDto(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();

        recipeIngredientDto.setIngredient(recipeIngredient.getIngredient().getName());
        recipeIngredientDto.setUnit(recipeIngredient.getUnit().getName());
        recipeIngredientDto.setQuantity(recipeIngredient.getQuantity().getAmount());

        return recipeIngredientDto;
    }

}
