package mealsapp.service;

import lombok.AllArgsConstructor;
import mealsapp.model.Quantity;
import mealsapp.repository.QuantityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class QuantityService {

    private final QuantityRepository quantityRepository;

    public Quantity addQuantity(Integer amount) {
        Quantity quantity = quantityRepository.findByAmount(amount);
        if (quantity == null) {
            quantity = new Quantity();
            quantity.setAmount(amount);
            quantityRepository.save(quantity);
        }
        return quantity;
    }

    public Quantity incrementQuantity(Quantity quantity, Quantity quantityToAdd) {
        Quantity returnQuantity = new Quantity();
        returnQuantity.setAmount(quantity.getAmount() + quantityToAdd.getAmount());
        quantityRepository.save(returnQuantity);

        return returnQuantity;
    }
}
