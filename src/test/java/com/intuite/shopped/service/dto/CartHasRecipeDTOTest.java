package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CartHasRecipeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartHasRecipeDTO.class);
        CartHasRecipeDTO cartHasRecipeDTO1 = new CartHasRecipeDTO();
        cartHasRecipeDTO1.setId(1L);
        CartHasRecipeDTO cartHasRecipeDTO2 = new CartHasRecipeDTO();
        assertThat(cartHasRecipeDTO1).isNotEqualTo(cartHasRecipeDTO2);
        cartHasRecipeDTO2.setId(cartHasRecipeDTO1.getId());
        assertThat(cartHasRecipeDTO1).isEqualTo(cartHasRecipeDTO2);
        cartHasRecipeDTO2.setId(2L);
        assertThat(cartHasRecipeDTO1).isNotEqualTo(cartHasRecipeDTO2);
        cartHasRecipeDTO1.setId(null);
        assertThat(cartHasRecipeDTO1).isNotEqualTo(cartHasRecipeDTO2);
    }
}
