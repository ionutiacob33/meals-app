package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.model.*;
import mealsapp.service.AuthService;
import mealsapp.service.IngredientService;
import mealsapp.service.QuantityService;
import mealsapp.service.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PantryMapper {

    private final AuthService authService;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final QuantityService quantityService;

    public PantryIngredientDto mapToDto(PantryIngredient pantryIngredient) {
        PantryIngredientDto pantryIngredientDto = new PantryIngredientDto();
        pantryIngredientDto.setId(pantryIngredient.getId());
        pantryIngredientDto.setUserId(pantryIngredient.getUserId());
        pantryIngredientDto.setIngredient(pantryIngredient.getIngredient().getName());
        pantryIngredientDto.setUnit(pantryIngredient.getUnit().getName());
        pantryIngredientDto.setQuantity(pantryIngredient.getQuantity().getAmount());
        return pantryIngredientDto;
    }

    public PantryIngredient mapToModel(PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredient = new PantryIngredient();

        pantryIngredient.setUserId(authService.getAuthenticatedUser().getId());
        pantryIngredient.setId(pantryIngredientDto.getId());

        Ingredient ingredient = ingredientService.addIngredient(pantryIngredientDto.getIngredient());
        pantryIngredient.setIngredient(ingredient);

        Unit unit = unitService.addUnit(pantryIngredientDto.getUnit());
        pantryIngredient.setUnit(unit);

        Quantity quantity = quantityService.addQuantity(pantryIngredientDto.getQuantity());
        pantryIngredient.setQuantity(quantity);

        return pantryIngredient;
    }
}
