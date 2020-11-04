package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeHasRecipeTagMapperTest {

    private RecipeHasRecipeTagMapper recipeHasRecipeTagMapper;

    @BeforeEach
    public void setUp() {
        recipeHasRecipeTagMapper = new RecipeHasRecipeTagMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipeHasRecipeTagMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipeHasRecipeTagMapper.fromId(null)).isNull();
    }
}
