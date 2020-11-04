package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientMapperTest {

    private IngredientMapper ingredientMapper;

    @BeforeEach
    public void setUp() {
        ingredientMapper = new IngredientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ingredientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ingredientMapper.fromId(null)).isNull();
    }
}
