package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeTagDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeTagDTO.class);
        RecipeTagDTO recipeTagDTO1 = new RecipeTagDTO();
        recipeTagDTO1.setId(1L);
        RecipeTagDTO recipeTagDTO2 = new RecipeTagDTO();
        assertThat(recipeTagDTO1).isNotEqualTo(recipeTagDTO2);
        recipeTagDTO2.setId(recipeTagDTO1.getId());
        assertThat(recipeTagDTO1).isEqualTo(recipeTagDTO2);
        recipeTagDTO2.setId(2L);
        assertThat(recipeTagDTO1).isNotEqualTo(recipeTagDTO2);
        recipeTagDTO1.setId(null);
        assertThat(recipeTagDTO1).isNotEqualTo(recipeTagDTO2);
    }
}
