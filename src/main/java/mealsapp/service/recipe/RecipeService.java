package mealsapp.service.recipe;

import lombok.AllArgsConstructor;
import mealsapp.dto.CookingTimeDto;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.IngredientDto;
import mealsapp.dto.StepDto;
import mealsapp.error.GenericException;
import mealsapp.mapper.RecipeMapper;
import mealsapp.model.recipe.CookingTime;
import mealsapp.model.recipe.Step;
import mealsapp.model.recipe.ingredient.Ingredient;
import mealsapp.model.recipe.Recipe;
import mealsapp.repository.recipe.RecipeRepository;
import mealsapp.service.AuthService;
import mealsapp.service.recipe.ingredient.IngredientService;
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
    private final StepService stepService;
    private final CookingTimeService cookingTimeService;
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
        recipe.setSource(recipeDto.getSource());
        recipe.setUrl(recipeDto.getUrl());
        recipe.setYeald(recipeDto.getYeald());

        List<Ingredient> ingredients = recipeMapper
                .mapIngredientsDtoToModel(recipeDto.getIngredients(), recipe);
        ingredientService.add(ingredients);
        List<Step> steps = recipeMapper
                .mapStepsDtoToModel(recipeDto.getSteps(), recipe);
        stepService.add(steps);
        List<CookingTime> cookingTimes = recipeMapper
                .mapCookingTimesDtoToModel(recipeDto.getCookingTimes(), recipe);
        cookingTimeService.add(cookingTimes);

        recipe = recipeRepository.save(recipe);
        return getDetailedRecipe(recipe.getId());
    }

    public List<RecipeDto> addRecipes(List<RecipeDto> recipeDtos) {
        List<RecipeDto> recipes = new ArrayList<>();

        for (RecipeDto recipeDto : recipeDtos) {
            recipes.add(addRecipe(recipeDto));
        }

        return recipes;
    }

    public RecipeDto updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeId);

        if (recipe.getUserId() != authService.getAuthenticatedUser().getId()) {
            throw new GenericException("User can only edit its own recipes");
        }

        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImageUrl(recipeDto.getImageUrl());
        recipe.setSource(recipeDto.getSource());
        recipe.setUrl(recipeDto.getUrl());
        recipe.setYeald(recipeDto.getYeald());
        recipe = recipeRepository.save(recipe);

        ingredientService.deleteByRecipeId(recipeId);
        List<Ingredient> ingredients =
                recipeMapper.mapIngredientsDtoToModel(recipeDto.getIngredients(), recipe);
        ingredientService.add(ingredients);

        stepService.deleteByRecipeId(recipeId);
        List<Step> steps =
                recipeMapper.mapStepsDtoToModel(recipeDto.getSteps(), recipe);
        stepService.add(steps);

        cookingTimeService.deleteByRecipeId(recipeId);
        List<CookingTime> cookingTimes =
                recipeMapper.mapCookingTimesDtoToModel(recipeDto.getCookingTimes(), recipe);
        cookingTimeService.add(cookingTimes);

        return getDetailedRecipe(recipe.getId());
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository
                .findAll()
                .stream()
                .map(recipe -> getDetailedRecipe(recipe.getId()))
                .collect(Collectors.toList());
    }

    public RecipeDto getDetailedRecipe(Long id) {
        RecipeDto detailedRecipe = new RecipeDto();
        Recipe recipe = recipeRepository.getById(id);

        detailedRecipe.setId(recipe.getId());
        detailedRecipe.setUserId(authService.getAuthenticatedUser().getId());
        detailedRecipe.setTitle(recipe.getTitle());
        detailedRecipe.setDescription(recipe.getDescription());
        detailedRecipe.setImageUrl(recipe.getImageUrl());
        detailedRecipe.setSource(recipe.getSource());
        detailedRecipe.setUrl(recipe.getUrl());
        detailedRecipe.setYeald(recipe.getYeald());

        List<Ingredient> ingredients = ingredientService.getByRecipeId(id);
        List<IngredientDto> recipeIngredientsDto = ingredients.stream()
                .map(recipeMapper::mapIngredientToDto)
                .toList();
        detailedRecipe.setIngredients(recipeIngredientsDto);
        List<Step> steps = stepService.getByRecipeId(id);
        List<StepDto> stepDtos = steps.stream()
                .map(recipeMapper::mapStepToDto)
                .toList();
        detailedRecipe.setSteps(stepDtos);
        List<CookingTime> cookingTimes = cookingTimeService.getByRecipeId(id);
        List<CookingTimeDto> cookingTimeDtos = cookingTimes.stream()
                .map(recipeMapper::mapCookingTimeToDto)
                .toList();
        detailedRecipe.setCookingTimes(cookingTimeDtos);

        return detailedRecipe;
    }

    public boolean deleteRecipe(Long id) {
        ingredientService.deleteByRecipeId(id);
        stepService.deleteByRecipeId(id);
        cookingTimeService.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return true;
    }

    public List<RecipeDto> getRecipesOfCurrentUser() {
        return recipeRepository
                .findByUserId(authService.getAuthenticatedUser().getId())
                .stream()
                .map(recipe -> getDetailedRecipe(recipe.getId()))
                .collect(Collectors.toList());
    }
}
