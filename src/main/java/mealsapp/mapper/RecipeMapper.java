package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.RecipeIngredientDto;
import mealsapp.model.*;
import mealsapp.service.IngredientService;
import mealsapp.service.QuantityService;
import mealsapp.service.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RecipeMapper {

    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final QuantityService quantityService;

    public List<RecipeIngredient> mapToModel(List<RecipeIngredientDto> recipeIngredientDtos, Recipe recipe) {
        return recipeIngredientDtos.stream()
                .map(recipeIngredientDto -> createModelFromDto(recipeIngredientDto, recipe))
                .collect(Collectors.toList());
    }

    public RecipeIngredient createModelFromDto(RecipeIngredientDto recipeIngredientDto, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setId(recipeIngredientDto.getId());

        Ingredient ingredient = ingredientService.addIngredient(recipeIngredientDto.getIngredient());
        recipeIngredient.setIngredient(ingredient);

        Unit unit = unitService.addUnit(recipeIngredientDto.getUnit());
        recipeIngredient.setUnit(unit);

        Quantity quantity = quantityService.addQuantity(recipeIngredientDto.getQuantity());
        recipeIngredient.setQuantity(quantity);

        return recipeIngredient;
    }

    public RecipeIngredientDto mapToDto(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();

        recipeIngredientDto.setId(recipeIngredient.getId());
        recipeIngredientDto.setIngredient(recipeIngredient.getIngredient().getName());
        recipeIngredientDto.setUnit(recipeIngredient.getUnit().getName());
        recipeIngredientDto.setQuantity(recipeIngredient.getQuantity().getAmount());

        return recipeIngredientDto;
    }

}
