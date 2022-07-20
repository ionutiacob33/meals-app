package mealsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mealsapp.model.ingredient.Name;
import mealsapp.model.ingredient.Amount;
import mealsapp.model.ingredient.Unit;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PantryIngredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "name_id", referencedColumnName = "id")
    private Name name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "amount_id", referencedColumnName = "id")
    private Amount amount;

}
