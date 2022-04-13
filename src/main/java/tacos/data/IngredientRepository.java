package tacos.data;

import tacos.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository {
    List<Ingredient> findAll();
    Optional<Ingredient> findOne(String id);
    Ingredient save(Ingredient ingredient);
}
