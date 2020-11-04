package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeHasRecipeTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeHasRecipeTag.class);
        RecipeHasRecipeTag recipeHasRecipeTag1 = new RecipeHasRecipeTag();
        recipeHasRecipeTag1.setId(1L);
        RecipeHasRecipeTag recipeHasRecipeTag2 = new RecipeHasRecipeTag();
        recipeHasRecipeTag2.setId(recipeHasRecipeTag1.getId());
        assertThat(recipeHasRecipeTag1).isEqualTo(recipeHasRecipeTag2);
        recipeHasRecipeTag2.setId(2L);
        assertThat(recipeHasRecipeTag1).isNotEqualTo(recipeHasRecipeTag2);
        recipeHasRecipeTag1.setId(null);
        assertThat(recipeHasRecipeTag1).isNotEqualTo(recipeHasRecipeTag2);
    }
}
