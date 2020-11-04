package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeSharedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeSharedDTO.class);
        RecipeSharedDTO recipeSharedDTO1 = new RecipeSharedDTO();
        recipeSharedDTO1.setId(1L);
        RecipeSharedDTO recipeSharedDTO2 = new RecipeSharedDTO();
        assertThat(recipeSharedDTO1).isNotEqualTo(recipeSharedDTO2);
        recipeSharedDTO2.setId(recipeSharedDTO1.getId());
        assertThat(recipeSharedDTO1).isEqualTo(recipeSharedDTO2);
        recipeSharedDTO2.setId(2L);
        assertThat(recipeSharedDTO1).isNotEqualTo(recipeSharedDTO2);
        recipeSharedDTO1.setId(null);
        assertThat(recipeSharedDTO1).isNotEqualTo(recipeSharedDTO2);
    }
}
