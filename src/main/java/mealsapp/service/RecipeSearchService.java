package mealsapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mealsapp.model.api.RecipeSearchByNameResponse;
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
    public static final Integer LIMIT_NUMBER = 5;

    public RecipeSearchByNameResponse getRecipe(String searchName) {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders());
        String queryUrl = SPOONACULAR_FULL_URL + RECIPES_COMPLEX_SEARCH + "?query=" + searchName + "&number=" + LIMIT_NUMBER.toString();

        ResponseEntity<String> response = restTemplate.exchange(queryUrl, GET, entity, String.class);

        System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
        if (response.hasBody()) {
            System.out.println(response.getBody());
            try {
                return objectMapper.readValue(response.getBody(), RecipeSearchByNameResponse.class);
            } catch (JsonProcessingException e) {
                System.out.println(e);
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

