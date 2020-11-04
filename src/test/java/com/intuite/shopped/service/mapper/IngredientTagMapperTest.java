package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientTagMapperTest {

    private IngredientTagMapper ingredientTagMapper;

    @BeforeEach
    public void setUp() {
        ingredientTagMapper = new IngredientTagMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ingredientTagMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ingredientTagMapper.fromId(null)).isNull();
    }
}
