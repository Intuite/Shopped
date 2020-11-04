package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeHasIngredientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeHasIngredientDTO.class);
        RecipeHasIngredientDTO recipeHasIngredientDTO1 = new RecipeHasIngredientDTO();
        recipeHasIngredientDTO1.setId(1L);
        RecipeHasIngredientDTO recipeHasIngredientDTO2 = new RecipeHasIngredientDTO();
        assertThat(recipeHasIngredientDTO1).isNotEqualTo(recipeHasIngredientDTO2);
        recipeHasIngredientDTO2.setId(recipeHasIngredientDTO1.getId());
        assertThat(recipeHasIngredientDTO1).isEqualTo(recipeHasIngredientDTO2);
        recipeHasIngredientDTO2.setId(2L);
        assertThat(recipeHasIngredientDTO1).isNotEqualTo(recipeHasIngredientDTO2);
        recipeHasIngredientDTO1.setId(null);
        assertThat(recipeHasIngredientDTO1).isNotEqualTo(recipeHasIngredientDTO2);
    }
}
