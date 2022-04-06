package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.mapper.RecipeMapper;
import mealsapp.model.*;
import mealsapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeMapper recipeMapper;

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());

        recipeRepository.save(recipe);
        List<RecipeIngredient> recipeIngredients = recipeMapper.mapToModel(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

        return recipe;
    }

    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.getById(recipeId);
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipeRepository.save(recipe);
        recipeIngredientService.deleteByRecipeId(recipeId);

        List<RecipeIngredient> recipeIngredients = recipeMapper.mapToModel(recipeDto.getRecipeIngredients(), recipe);
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
                .map(recipeMapper::mapToDto)
                .toList();
        detailedRecipe.setRecipeIngredients(recipeIngredientsDto);
        return detailedRecipe;
    }

    public boolean deleteRecipe(Long id) {
        recipeIngredientService.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
        return true;
    }

}
