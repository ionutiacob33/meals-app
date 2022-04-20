package mealsapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mealsapp.model.api.recipeSummary.RecipeSummaryResponse;
import mealsapp.model.api.searchByIngredient.RecipeByIngredientsItem;
import mealsapp.model.api.searchByName.RecipeByNameResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static mealsapp.utils.Constants.*;
import static org.springframework.http.HttpMethod.GET;

@Service
@AllArgsConstructor
@Transactional
public class RecipeSearchService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public static final String RECIPES_COMPLEX_SEARCH = "/recipes/complexSearch";
    public static final String RECIPES_SEARCH_BY_INGREDIENTS = "/recipes/findByIngredients";
    public static final Integer LIMIT_NUMBER = 1;

    public RecipeByNameResponse getRecipesByName(String searchName) {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders());
        StringBuilder queryUrl = new StringBuilder();

        queryUrl.append(SPOONACULAR_FULL_URL)
                .append(RECIPES_COMPLEX_SEARCH)
                .append("?query=")
                .append(searchName)
                .append("&number=")
                .append(LIMIT_NUMBER);

        ResponseEntity<String> response = restTemplate.exchange(queryUrl.toString(), GET, entity, String.class);

        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        if (response.hasBody()) {
            System.out.println(response.getBody());
            try {
                return objectMapper.readValue(response.getBody(), RecipeByNameResponse.class);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public List<RecipeByIngredientsItem> getRecipesByIngredients(List<String> ingredients) {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders());
        StringBuilder queryUrl = new StringBuilder();

        queryUrl.append(SPOONACULAR_FULL_URL)
                .append(RECIPES_SEARCH_BY_INGREDIENTS)
                .append("?ingredients=");
        for (int i = 0; i < ingredients.size(); i++) {
            if (i == 0) {
                queryUrl.append(ingredients.get(i));
            } else {
                queryUrl.append(",+").append(ingredients.get(i));
            }
        }
        queryUrl.append("&number=")
                .append(LIMIT_NUMBER);

        ResponseEntity<String> response = restTemplate.exchange(queryUrl.toString(), GET, entity, String.class);

        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        if (response.hasBody()) {
            System.out.println(response.getBody());
            try {
                return objectMapper.readValue(response.getBody(), new TypeReference<List<RecipeByIngredientsItem>>() {});
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public RecipeSummaryResponse getRecipeSummary(Long recipeId) {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders());
        StringBuilder queryUrl = new StringBuilder();

        queryUrl.append(SPOONACULAR_FULL_URL)
                .append("/recipes/")
                .append(recipeId)
                .append("/summary");

        ResponseEntity<String> response = restTemplate.exchange(queryUrl.toString(), GET, entity, String.class);

        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        if (response.hasBody()) {
            System.out.println(response.getBody());
            try {
                return objectMapper.readValue(response.getBody(), RecipeSummaryResponse.class);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_RAPID_API_HOST, SPOONACULAR_URL);
        headers.add(X_RAPID_API_KEY, API_KEY);
        return headers;
    }

}

