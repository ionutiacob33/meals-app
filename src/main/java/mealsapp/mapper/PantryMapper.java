package mealsapp.mapper;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.model.*;
import mealsapp.model.recipe.ingredient.Name;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.model.recipe.ingredient.Unit;
import mealsapp.service.AuthService;
import mealsapp.service.recipe.ingredient.NameService;
import mealsapp.service.recipe.ingredient.AmountService;
import mealsapp.service.recipe.ingredient.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PantryMapper {

    private final AuthService authService;
    private final NameService nameService;
    private final UnitService unitService;
    private final AmountService amountService;

    public PantryIngredientDto mapToDto(PantryIngredient pantryIngredient) {
        PantryIngredientDto pantryIngredientDto = new PantryIngredientDto();
        pantryIngredientDto.setName(pantryIngredient.getName().getName());
        pantryIngredientDto.setUnit(pantryIngredient.getUnit().getUnit());
        pantryIngredientDto.setAmount(pantryIngredient.getAmount().getAmount());
        return pantryIngredientDto;
    }

    public PantryIngredient mapToModel(PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredient = new PantryIngredient();

        pantryIngredient.setUser(authService.getAuthenticatedUser());
        pantryIngredient.setId(pantryIngredientDto.getId());

        Name name = nameService.addName(pantryIngredientDto.getName());
        pantryIngredient.setName(name);

        Unit unit = unitService.addUnit(pantryIngredientDto.getUnit());
        pantryIngredient.setUnit(unit);

        Amount amount = amountService.addQuantity(pantryIngredientDto.getAmount());
        pantryIngredient.setAmount(amount);

        return pantryIngredient;
    }
}
