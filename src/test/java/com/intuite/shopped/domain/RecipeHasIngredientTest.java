package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeHasIngredientTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeHasIngredient.class);
        RecipeHasIngredient recipeHasIngredient1 = new RecipeHasIngredient();
        recipeHasIngredient1.setId(1L);
        RecipeHasIngredient recipeHasIngredient2 = new RecipeHasIngredient();
        recipeHasIngredient2.setId(recipeHasIngredient1.getId());
        assertThat(recipeHasIngredient1).isEqualTo(recipeHasIngredient2);
        recipeHasIngredient2.setId(2L);
        assertThat(recipeHasIngredient1).isNotEqualTo(recipeHasIngredient2);
        recipeHasIngredient1.setId(null);
        assertThat(recipeHasIngredient1).isNotEqualTo(recipeHasIngredient2);
    }
}
