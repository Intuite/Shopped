package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class IngredientTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientTag.class);
        IngredientTag ingredientTag1 = new IngredientTag();
        ingredientTag1.setId(1L);
        IngredientTag ingredientTag2 = new IngredientTag();
        ingredientTag2.setId(ingredientTag1.getId());
        assertThat(ingredientTag1).isEqualTo(ingredientTag2);
        ingredientTag2.setId(2L);
        assertThat(ingredientTag1).isNotEqualTo(ingredientTag2);
        ingredientTag1.setId(null);
        assertThat(ingredientTag1).isNotEqualTo(ingredientTag2);
    }
}
