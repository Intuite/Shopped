package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class IngredientHasIngredientTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientHasIngredientTag.class);
        IngredientHasIngredientTag ingredientHasIngredientTag1 = new IngredientHasIngredientTag();
        ingredientHasIngredientTag1.setId(1L);
        IngredientHasIngredientTag ingredientHasIngredientTag2 = new IngredientHasIngredientTag();
        ingredientHasIngredientTag2.setId(ingredientHasIngredientTag1.getId());
        assertThat(ingredientHasIngredientTag1).isEqualTo(ingredientHasIngredientTag2);
        ingredientHasIngredientTag2.setId(2L);
        assertThat(ingredientHasIngredientTag1).isNotEqualTo(ingredientHasIngredientTag2);
        ingredientHasIngredientTag1.setId(null);
        assertThat(ingredientHasIngredientTag1).isNotEqualTo(ingredientHasIngredientTag2);
    }
}
