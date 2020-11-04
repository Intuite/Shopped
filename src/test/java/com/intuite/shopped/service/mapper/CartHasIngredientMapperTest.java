package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartHasIngredientMapperTest {

    private CartHasIngredientMapper cartHasIngredientMapper;

    @BeforeEach
    public void setUp() {
        cartHasIngredientMapper = new CartHasIngredientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cartHasIngredientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cartHasIngredientMapper.fromId(null)).isNull();
    }
}
