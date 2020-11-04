package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class IngredientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientDTO.class);
        IngredientDTO ingredientDTO1 = new IngredientDTO();
        ingredientDTO1.setId(1L);
        IngredientDTO ingredientDTO2 = new IngredientDTO();
        assertThat(ingredientDTO1).isNotEqualTo(ingredientDTO2);
        ingredientDTO2.setId(ingredientDTO1.getId());
        assertThat(ingredientDTO1).isEqualTo(ingredientDTO2);
        ingredientDTO2.setId(2L);
        assertThat(ingredientDTO1).isNotEqualTo(ingredientDTO2);
        ingredientDTO1.setId(null);
        assertThat(ingredientDTO1).isNotEqualTo(ingredientDTO2);
    }
}
