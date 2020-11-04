package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class RecipeTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeTag.class);
        RecipeTag recipeTag1 = new RecipeTag();
        recipeTag1.setId(1L);
        RecipeTag recipeTag2 = new RecipeTag();
        recipeTag2.setId(recipeTag1.getId());
        assertThat(recipeTag1).isEqualTo(recipeTag2);
        recipeTag2.setId(2L);
        assertThat(recipeTag1).isNotEqualTo(recipeTag2);
        recipeTag1.setId(null);
        assertThat(recipeTag1).isNotEqualTo(recipeTag2);
    }
}
