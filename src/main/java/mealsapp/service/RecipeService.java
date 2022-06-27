package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.mapper.RecipeMapper;
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
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeStepService recipeStepService;
    private final RecipeMapper recipeMapper;
    private final AuthService authService;

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        if (recipeDto.getId() != null) {
            recipe.setId(recipeDto.getId());
        }
        recipe.setUser(authService.getAuthenticatedUser());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImageUrl(recipeDto.getImageUrl());
        recipe.setApiId(recipeDto.getApiId());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setCarbs(recipeDto.getCarbs());
        recipe.setProtein(recipeDto.getProtein());
        recipe.setFat(recipeDto.getFat());

        recipeRepository.save(recipe);
        List<RecipeIngredient> recipeIngredients = recipeMapper
                .mapIngredientsDtoToModel(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);
        List<RecipeStep> recipeSteps = recipeMapper
                .mapStepsDtoToModel(recipeDto.getRecipeSteps(), recipe);
        recipeStepService.add(recipeSteps);

        return recipe;
    }

    public List<Recipe> addRecipes(List<RecipeDto> recipeDtos) {
        List<Recipe> recipes = new ArrayList<>();

        for (RecipeDto recipeDto : recipeDtos) {
            recipes.add(addRecipe(recipeDto));
        }

        return recipes;
    }

    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeId);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipeRepository.save(recipe);
        recipeIngredientService.deleteByRecipeId(recipeId);

        List<RecipeIngredient> recipeIngredients = recipeMapper.mapIngredientsDtoToModel(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

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
        detailedRecipe.setTitle(recipe.getTitle());
        detailedRecipe.setDescription(recipe.getDescription());
        detailedRecipe.setImageUrl(recipe.getImageUrl());
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByRecipeId(id);
        List<RecipeIngredientDto> recipeIngredientsDto = recipeIngredients.stream()
                .map(recipeMapper::mapIngredientToDto)
                .toList();
        detailedRecipe.setRecipeIngredients(recipeIngredientsDto);
        List<RecipeStep> recipeSteps = recipeStepService.getByRecipeId(id);
        List<StepDto> recipeStepsDto = recipeSteps.stream()
                .map(recipeMapper::mapStepToDto)
                .toList();
        detailedRecipe.setRecipeSteps(recipeStepsDto);
        return detailedRecipe;
    }

    public boolean deleteRecipe(Long id) {
        recipeIngredientService.deleteByRecipeId(id);
        recipeStepService.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return true;
    }

    public List<RecipeDto> getRecipesOfCurrentUser() {
        return recipeRepository.findByUser(authService.getAuthenticatedUser())
                .stream()
                .map(recipe -> getDetailedRecipe(recipe.getId()))
                .collect(Collectors.toList());
    }
}
