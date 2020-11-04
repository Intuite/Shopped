package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeHasIngredientMapperTest {

    private RecipeHasIngredientMapper recipeHasIngredientMapper;

    @BeforeEach
    public void setUp() {
        recipeHasIngredientMapper = new RecipeHasIngredientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipeHasIngredientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipeHasIngredientMapper.fromId(null)).isNull();
    }
}
