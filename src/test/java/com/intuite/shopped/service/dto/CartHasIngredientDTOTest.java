package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CartHasIngredientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartHasIngredientDTO.class);
        CartHasIngredientDTO cartHasIngredientDTO1 = new CartHasIngredientDTO();
        cartHasIngredientDTO1.setId(1L);
        CartHasIngredientDTO cartHasIngredientDTO2 = new CartHasIngredientDTO();
        assertThat(cartHasIngredientDTO1).isNotEqualTo(cartHasIngredientDTO2);
        cartHasIngredientDTO2.setId(cartHasIngredientDTO1.getId());
        assertThat(cartHasIngredientDTO1).isEqualTo(cartHasIngredientDTO2);
        cartHasIngredientDTO2.setId(2L);
        assertThat(cartHasIngredientDTO1).isNotEqualTo(cartHasIngredientDTO2);
        cartHasIngredientDTO1.setId(null);
        assertThat(cartHasIngredientDTO1).isNotEqualTo(cartHasIngredientDTO2);
    }
}
