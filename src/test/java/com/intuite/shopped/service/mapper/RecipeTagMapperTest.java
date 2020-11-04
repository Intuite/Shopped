package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeTagMapperTest {

    private RecipeTagMapper recipeTagMapper;

    @BeforeEach
    public void setUp() {
        recipeTagMapper = new RecipeTagMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipeTagMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipeTagMapper.fromId(null)).isNull();
    }
}
