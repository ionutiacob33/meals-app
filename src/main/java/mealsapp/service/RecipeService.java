package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeDto;
import mealsapp.dto.RecipeIngredientDto;
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
    private final RecipeMapper recipeMapper;

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        if (recipeDto.getId() != null) {
            recipe.setId(recipeDto.getId());
        }
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setImageUrl(recipeDto.getImageUrl());
        recipe.setApiId(recipeDto.getApiId());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setCarbs(recipeDto.getCarbs());
        recipe.setProtein(recipeDto.getProtein());
        recipe.setFat(recipeDto.getFat());

        recipeRepository.save(recipe);
        List<RecipeIngredient> recipeIngredients = recipeMapper.mapToModel(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

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

        List<RecipeIngredient> recipeIngredients = recipeMapper.mapToModel(recipeDto.getRecipeIngredients(), recipe);
        recipeIngredientService.add(recipeIngredients);

        return recipe;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream().map(recipe -> getRecipe(recipe.getId())).collect(Collectors.toList());
    }

    public RecipeDto getRecipe(Long id) {
        RecipeDto detailedRecipe = new RecipeDto();
        Recipe recipe = recipeRepository.getById(id);
        detailedRecipe.setId(recipe.getId());
        detailedRecipe.setApiId(recipe.getApiId());
        detailedRecipe.setTitle(recipe.getTitle());
        detailedRecipe.setDescription(recipe.getDescription());
        detailedRecipe.setImageUrl(recipe.getImageUrl());
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
