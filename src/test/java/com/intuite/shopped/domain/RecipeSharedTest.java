package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeSharedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeShared.class);
        RecipeShared recipeShared1 = new RecipeShared();
        recipeShared1.setId(1L);
        RecipeShared recipeShared2 = new RecipeShared();
        recipeShared2.setId(recipeShared1.getId());
        assertThat(recipeShared1).isEqualTo(recipeShared2);
        recipeShared2.setId(2L);
        assertThat(recipeShared1).isNotEqualTo(recipeShared2);
        recipeShared1.setId(null);
        assertThat(recipeShared1).isNotEqualTo(recipeShared2);
    }
}
