package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class IngredientTagDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientTagDTO.class);
        IngredientTagDTO ingredientTagDTO1 = new IngredientTagDTO();
        ingredientTagDTO1.setId(1L);
        IngredientTagDTO ingredientTagDTO2 = new IngredientTagDTO();
        assertThat(ingredientTagDTO1).isNotEqualTo(ingredientTagDTO2);
        ingredientTagDTO2.setId(ingredientTagDTO1.getId());
        assertThat(ingredientTagDTO1).isEqualTo(ingredientTagDTO2);
        ingredientTagDTO2.setId(2L);
        assertThat(ingredientTagDTO1).isNotEqualTo(ingredientTagDTO2);
        ingredientTagDTO1.setId(null);
        assertThat(ingredientTagDTO1).isNotEqualTo(ingredientTagDTO2);
    }
}
