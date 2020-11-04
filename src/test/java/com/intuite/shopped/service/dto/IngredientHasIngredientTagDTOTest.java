package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class IngredientHasIngredientTagDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientHasIngredientTagDTO.class);
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO1 = new IngredientHasIngredientTagDTO();
        ingredientHasIngredientTagDTO1.setId(1L);
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO2 = new IngredientHasIngredientTagDTO();
        assertThat(ingredientHasIngredientTagDTO1).isNotEqualTo(ingredientHasIngredientTagDTO2);
        ingredientHasIngredientTagDTO2.setId(ingredientHasIngredientTagDTO1.getId());
        assertThat(ingredientHasIngredientTagDTO1).isEqualTo(ingredientHasIngredientTagDTO2);
        ingredientHasIngredientTagDTO2.setId(2L);
        assertThat(ingredientHasIngredientTagDTO1).isNotEqualTo(ingredientHasIngredientTagDTO2);
        ingredientHasIngredientTagDTO1.setId(null);
        assertThat(ingredientHasIngredientTagDTO1).isNotEqualTo(ingredientHasIngredientTagDTO2);
    }
}
