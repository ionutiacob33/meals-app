package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.IngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.mapper.RecipeMapper;
import mealsapp.model.*;
import mealsapp.model.ingredient.Ingredient;
import mealsapp.model.step.RecipeStep;
import mealsapp.repository.*;
import mealsapp.service.ingredient.IngredientService;
import mealsapp.service.step.RecipeStepService;
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
    private final IngredientService ingredientService;
    private final RecipeStepService recipeStepService;
    private final RecipeMapper recipeMapper;
    private final AuthService authService;

    public RecipeDto addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        if (recipeDto.getId() != null) {
            recipe.setId(recipeDto.getId());
        }
        recipe.setUserId(authService.getAuthenticatedUser().getId());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImageUrl(recipeDto.getImageUrl());
        recipe.setApiId(recipeDto.getApiId());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setCarbs(recipeDto.getCarbs());
        recipe.setProtein(recipeDto.getProtein());
        recipe.setFat(recipeDto.getFat());

        recipe = recipeRepository.save(recipe);
        List<Ingredient> ingredients = recipeMapper
                .mapIngredientsDtoToModel(recipeDto.getIngredients(), recipe);
        ingredientService.add(ingredients);
        List<RecipeStep> recipeSteps = recipeMapper
                .mapStepsDtoToModel(recipeDto.getRecipeSteps(), recipe);
        recipeStepService.add(recipeSteps);

        return getDetailedRecipe(recipe.getId());
    }

    public List<RecipeDto> addRecipes(List<RecipeDto> recipeDtos) {
        List<RecipeDto> recipes = new ArrayList<>();

        for (RecipeDto recipeDto : recipeDtos) {
            recipes.add(addRecipe(recipeDto));
        }

        return recipes;
    }

    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeId);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImageUrl(recipeDto.getImageUrl());
        recipe.setApiId(recipeDto.getApiId());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setCarbs(recipeDto.getCarbs());
        recipe.setProtein(recipeDto.getProtein());
        recipe.setFat(recipeDto.getFat());
        recipe = recipeRepository.save(recipe);

        ingredientService.deleteByRecipeId(recipeId);
        List<Ingredient> ingredients = recipeMapper.mapIngredientsDtoToModel(recipeDto.getIngredients(), recipe);
        ingredientService.add(ingredients);

        recipeStepService.deleteByRecipeId(recipeId);
        List<RecipeStep> recipeSteps = recipeMapper.mapStepsDtoToModel(recipeDto.getRecipeSteps(), recipe);
        recipeStepService.add(recipeSteps);

        return recipe;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream().map(recipe -> getDetailedRecipe(recipe.getId())).collect(Collectors.toList());
    }

    public RecipeDto getDetailedRecipe(Long id) {
        RecipeDto detailedRecipe = new RecipeDto();
        Recipe recipe = recipeRepository.getById(id);
        detailedRecipe.setId(recipe.getId());
        detailedRecipe.setApiId(recipe.getApiId());
        detailedRecipe.setUserId(authService.getAuthenticatedUser().getId());
        detailedRecipe.setTitle(recipe.getTitle());
        detailedRecipe.setDescription(recipe.getDescription());
        detailedRecipe.setImageUrl(recipe.getImageUrl());
        List<Ingredient> ingredients = ingredientService.getByRecipeId(id);
        List<IngredientDto> recipeIngredientsDto = ingredients.stream()
                .map(recipeMapper::mapIngredientToDto)
                .toList();
        detailedRecipe.setIngredients(recipeIngredientsDto);
        List<RecipeStep> recipeSteps = recipeStepService.getByRecipeId(id);
        List<StepDto> recipeStepsDto = recipeSteps.stream()
                .map(recipeMapper::mapStepToDto)
                .toList();
        detailedRecipe.setRecipeSteps(recipeStepsDto);
        return detailedRecipe;
    }

    public boolean deleteRecipe(Long id) {
        ingredientService.deleteByRecipeId(id);
        recipeStepService.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return true;
    }

    public List<RecipeDto> getRecipesOfCurrentUser() {
        return recipeRepository.findByUserId(authService.getAuthenticatedUser().getId())
                .stream()
                .map(recipe -> getDetailedRecipe(recipe.getId()))
                .collect(Collectors.toList());
    }
}
