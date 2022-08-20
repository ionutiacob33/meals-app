package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.dto.ShoppingListIngredientDto;
import mealsapp.error.GenericException;
import mealsapp.mapper.PantryMapper;
import mealsapp.model.PantryIngredient;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.repository.PantryIngredientRepository;
import mealsapp.service.recipe.ingredient.AmountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PantryIngredientService {

    private final PantryIngredientRepository pantryIngredientRepository;
    private final PantryMapper pantryMapper;
    private final AmountService amountService;
    private final AuthService authService;

    public PantryIngredientDto addIngredient(PantryIngredientDto pantryIngredientDto) {
        pantryIngredientDto = toLower(pantryIngredientDto);

        PantryIngredient pantryIngredientMap = pantryMapper.mapToModel(pantryIngredientDto);
        PantryIngredient pantryIngredient = pantryIngredientRepository
                .findByUserAndNameAndUnit(
                        authService.getAuthenticatedUser(),
                        pantryIngredientMap.getName(),
                        pantryIngredientMap.getUnit()
                );

        if (pantryIngredient != null) {
            Amount amount = amountService.incrementAmount(pantryIngredient.getAmount(), pantryIngredientMap.getAmount());
            pantryIngredient.setAmount(amount);
        } else {
            pantryIngredient = new PantryIngredient();
            pantryIngredient.setName(pantryIngredientMap.getName());
            pantryIngredient.setUnit(pantryIngredientMap.getUnit());
            pantryIngredient.setAmount(pantryIngredientMap.getAmount());
        }
        pantryIngredientRepository.save(pantryIngredient);

        return pantryMapper.mapToDto(pantryIngredient);
    }

    public PantryIngredientDto editIngredient(Long id, PantryIngredientDto pantryIngredientDto) {
        pantryIngredientDto = toLower(pantryIngredientDto);

        PantryIngredient pantryIngredient = pantryIngredientRepository.getById(id);
        PantryIngredient pantryIngredientMap = pantryMapper.mapToModel(pantryIngredientDto);

        pantryIngredient.setName(pantryIngredientMap.getName());
        pantryIngredient.setUnit(pantryIngredientMap.getUnit());
        pantryIngredient.setAmount(pantryIngredientMap.getAmount());

        pantryIngredientRepository.save(pantryIngredient);

        return pantryIngredientDto;
    }

    public List<PantryIngredientDto> getIngredientsOfCurrentUser() {
        Long userId = authService.getAuthenticatedUser().getId();
        List<PantryIngredient> pantryIngredients = pantryIngredientRepository.findByUserId(userId);

        return pantryIngredients.stream()
                .map(pantryMapper::mapToDto)
                .toList();
    }

    public boolean deleteIngredient(Long id) {
        PantryIngredient pantryIngredient = pantryIngredientRepository.getById(id);
        Long userId = pantryIngredient.getUser().getId();
        Long authUserId = authService.getAuthenticatedUser().getId();

        if (userId != authUserId) {
            throw new GenericException("User can only delete its own pantry ingredients");
        }

        pantryIngredientRepository.deleteById(id);

        return true;
    }

    private PantryIngredientDto toLower(PantryIngredientDto pantryIngredientDto) {
        String lowerName = pantryIngredientDto.getName().toLowerCase();
        String lowerUnit = pantryIngredientDto.getUnit().toLowerCase();

        pantryIngredientDto.setName(lowerName);
        pantryIngredientDto.setUnit(lowerUnit);

        return pantryIngredientDto;
    }

}
