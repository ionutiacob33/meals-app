package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.model.Ingredient;
import mealsapp.model.PantryIngredient;
import mealsapp.model.Quantity;
import mealsapp.model.Unit;
import mealsapp.repository.PantryIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PantryIngredientService {

    private final PantryIngredientRepository pantryIngredientRepository;
    private final AuthService authService;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final QuantityService quantityService;

    public PantryIngredientDto addIngredient(PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredient = new PantryIngredient();

        pantryIngredient.setUser(authService.getAuthenticatedUser());

        Ingredient ingredient = ingredientService.addIngredient(pantryIngredientDto.getIngredient());
        pantryIngredient.setIngredient(ingredient);

        Unit unit = unitService.addUnit(pantryIngredientDto.getUnit());
        pantryIngredient.setUnit(unit);

        Quantity quantity = quantityService.addQuantity(pantryIngredientDto.getQuantity());
        pantryIngredient.setQuantity(quantity);

        pantryIngredientRepository.save(pantryIngredient);
        return pantryIngredientDto;
    }

    public List<PantryIngredientDto> getIngredients(Long userId) {
        List<PantryIngredient> pantryIngredients = pantryIngredientRepository.findByUserId(userId);

        return pantryIngredients.stream()
                .map(this::mapToDto)
                .toList();
    }

    public PantryIngredientDto mapToDto(PantryIngredient pantryIngredient) {
        PantryIngredientDto pantryIngredientDto = new PantryIngredientDto();
        pantryIngredientDto.setIngredient(pantryIngredient.getIngredient().getName());
        pantryIngredientDto.setUnit(pantryIngredient.getUnit().getName());
        pantryIngredientDto.setQuantity(pantryIngredient.getQuantity().getAmount());
        return pantryIngredientDto;
    }
}
