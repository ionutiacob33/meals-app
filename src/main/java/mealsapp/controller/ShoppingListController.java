package mealsapp.controller;

import lombok.AllArgsConstructor;
import mealsapp.dto.PantryIngredientDto;
import mealsapp.dto.ShoppingListIngredientDto;
import mealsapp.model.Response;
import mealsapp.service.ShoppingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/shopping")
@AllArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping
    public ResponseEntity<Response> addIngredient(@RequestBody ShoppingListIngredientDto shoppingListIngredientDto) {
        ShoppingListIngredientDto shoppingListIngredient = shoppingListService.addIngredient(shoppingListIngredientDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("shoppingListIngredient", shoppingListIngredient))
                        .message("Ingredient added to the shopping list successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/multiple")
    public ResponseEntity<Response> addIngredient(@RequestBody List<ShoppingListIngredientDto> shoppingListIngredientDtos) {
        List<ShoppingListIngredientDto> shoppingListIngredients = shoppingListService.addIngredients(shoppingListIngredientDtos);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("shoppingListIngredients", shoppingListIngredients))
                        .message("Ingredients added to the shopping list successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @PutMapping("/{ingrId}")
    public ResponseEntity<Response> editIngredient(@PathVariable Long ingrId, @RequestBody ShoppingListIngredientDto shoppingListIngredientDto) {
        ShoppingListIngredientDto shoppingListIngredient = shoppingListService.editIngredient(ingrId, shoppingListIngredientDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("shoppingListIngredient", shoppingListIngredient))
                        .message("Shopping list ingredient edited successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/user")
    public ResponseEntity<Response> getIngredients() {
        List<ShoppingListIngredientDto> shoppingListIngredients = shoppingListService.getIngredientsOfCurrentUser();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("shoppingListIngredients", shoppingListIngredients))
                        .message("Shopping list ingredients for the current user retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleteSuccess", shoppingListService.deleteIngredient(id)))
                        .message("Ingredient " + id + " deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

}
