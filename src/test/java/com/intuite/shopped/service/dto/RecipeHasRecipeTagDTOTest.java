package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeHasRecipeTagDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeHasRecipeTagDTO.class);
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO1 = new RecipeHasRecipeTagDTO();
        recipeHasRecipeTagDTO1.setId(1L);
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO2 = new RecipeHasRecipeTagDTO();
        assertThat(recipeHasRecipeTagDTO1).isNotEqualTo(recipeHasRecipeTagDTO2);
        recipeHasRecipeTagDTO2.setId(recipeHasRecipeTagDTO1.getId());
        assertThat(recipeHasRecipeTagDTO1).isEqualTo(recipeHasRecipeTagDTO2);
        recipeHasRecipeTagDTO2.setId(2L);
        assertThat(recipeHasRecipeTagDTO1).isNotEqualTo(recipeHasRecipeTagDTO2);
        recipeHasRecipeTagDTO1.setId(null);
        assertThat(recipeHasRecipeTagDTO1).isNotEqualTo(recipeHasRecipeTagDTO2);
    }
}
