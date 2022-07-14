package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.error.GenericException;
import mealsapp.mapper.PantryMapper;
import mealsapp.model.PantryIngredient;
import mealsapp.model.Quantity;
import mealsapp.repository.PantryIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PantryIngredientService {

    private final PantryIngredientRepository pantryIngredientRepository;
    private final PantryMapper pantryMapper;
    private final QuantityService quantityService;
    private final AuthService authService;

    public PantryIngredientDto addIngredient(PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredientMap = pantryMapper.mapToModel(pantryIngredientDto);
        PantryIngredient pantryIngredient = pantryIngredientRepository
                .findByIngredientAndUnit(pantryIngredientMap.getIngredient(), pantryIngredientMap.getUnit());

        if (pantryIngredient != null) {
            Quantity quantity = quantityService.incrementQuantity(pantryIngredient.getQuantity(), pantryIngredientMap.getQuantity());
            pantryIngredient.setQuantity(quantity);
        } else {
            pantryIngredient = new PantryIngredient();
            pantryIngredient.setUserId(authService.getAuthenticatedUser().getId());
            pantryIngredient.setIngredient(pantryIngredientMap.getIngredient());
            pantryIngredient.setUnit(pantryIngredientMap.getUnit());
            pantryIngredient.setQuantity(pantryIngredientMap.getQuantity());
        }
        pantryIngredientRepository.save(pantryIngredient);

        return pantryMapper.mapToDto(pantryIngredient);
    }

    public PantryIngredientDto editIngredient(Long id, PantryIngredientDto pantryIngredientDto) {
        PantryIngredient pantryIngredient = pantryIngredientRepository.getById(id);
        PantryIngredient pantryIngredientMap = pantryMapper.mapToModel(pantryIngredientDto);

        pantryIngredient.setIngredient(pantryIngredientMap.getIngredient());
        pantryIngredient.setUnit(pantryIngredientMap.getUnit());
        pantryIngredient.setQuantity(pantryIngredientMap.getQuantity());

        pantryIngredientRepository.save(pantryIngredient);

        return pantryIngredientDto;
    }

    public List<PantryIngredientDto> getIngredients(Long userId) {
        List<PantryIngredient> pantryIngredients = pantryIngredientRepository.findByUserId(userId);

        return pantryIngredients.stream()
                .map(pantryMapper::mapToDto)
                .toList();
    }

    public boolean deleteIngredient(Long id) {
        PantryIngredient pantryIngredient = pantryIngredientRepository.getById(id);
        Long userId = pantryIngredient.getUserId();
        Long authUserId = authService.getAuthenticatedUser().getId();
        if (userId != authUserId) {
            throw new GenericException("User can only delete its own pantry ingredients");
        }
        pantryIngredientRepository.deleteById(id);
        return true;
    }

    public List<PantryIngredientDto> getIngredientsOfCurrentUser() {
        return pantryIngredientRepository.findByUserId(authService.getAuthenticatedUser().getId())
                .stream()
                .map(pantryMapper :: mapToDto)
                .collect(Collectors.toList());
    }
}
