package mealsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookingTimeDto {
    private Long id;
    private String title;
    private Integer minutes;
    private Integer hours;
}
