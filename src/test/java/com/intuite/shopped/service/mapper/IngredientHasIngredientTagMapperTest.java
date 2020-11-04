package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientHasIngredientTagMapperTest {

    private IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper;

    @BeforeEach
    public void setUp() {
        ingredientHasIngredientTagMapper = new IngredientHasIngredientTagMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ingredientHasIngredientTagMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ingredientHasIngredientTagMapper.fromId(null)).isNull();
    }
}
