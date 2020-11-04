package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CartHasIngredientTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartHasIngredient.class);
        CartHasIngredient cartHasIngredient1 = new CartHasIngredient();
        cartHasIngredient1.setId(1L);
        CartHasIngredient cartHasIngredient2 = new CartHasIngredient();
        cartHasIngredient2.setId(cartHasIngredient1.getId());
        assertThat(cartHasIngredient1).isEqualTo(cartHasIngredient2);
        cartHasIngredient2.setId(2L);
        assertThat(cartHasIngredient1).isNotEqualTo(cartHasIngredient2);
        cartHasIngredient1.setId(null);
        assertThat(cartHasIngredient1).isNotEqualTo(cartHasIngredient2);
    }
}
