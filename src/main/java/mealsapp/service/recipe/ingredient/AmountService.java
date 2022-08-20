package mealsapp.service.recipe.ingredient;

import lombok.AllArgsConstructor;
import mealsapp.model.recipe.ingredient.Amount;
import mealsapp.repository.recipe.ingredient.AmountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AmountService {

    private final AmountRepository amountRepository;

    public Amount addAmount(Double amount) {
        Amount amountData = amountRepository.findByAmount(amount);
        if (amountData == null) {
            amountData = new Amount();
            amountData.setAmount(amount);
            amountRepository.save(amountData);
        }
        return amountData;
    }

    public Amount incrementAmount(Amount amount, Amount amountToAdd) {
        Amount returnAmount = new Amount();
        returnAmount.setAmount(amount.getAmount() + amountToAdd.getAmount());
        amountRepository.save(returnAmount);

        return returnAmount;
    }
}
