package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartHasRecipeMapperTest {

    private CartHasRecipeMapper cartHasRecipeMapper;

    @BeforeEach
    public void setUp() {
        cartHasRecipeMapper = new CartHasRecipeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cartHasRecipeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cartHasRecipeMapper.fromId(null)).isNull();
    }
}
