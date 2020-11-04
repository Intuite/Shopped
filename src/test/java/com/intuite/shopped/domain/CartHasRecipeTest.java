package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CartHasRecipeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartHasRecipe.class);
        CartHasRecipe cartHasRecipe1 = new CartHasRecipe();
        cartHasRecipe1.setId(1L);
        CartHasRecipe cartHasRecipe2 = new CartHasRecipe();
        cartHasRecipe2.setId(cartHasRecipe1.getId());
        assertThat(cartHasRecipe1).isEqualTo(cartHasRecipe2);
        cartHasRecipe2.setId(2L);
        assertThat(cartHasRecipe1).isNotEqualTo(cartHasRecipe2);
        cartHasRecipe1.setId(null);
        assertThat(cartHasRecipe1).isNotEqualTo(cartHasRecipe2);
    }
}
