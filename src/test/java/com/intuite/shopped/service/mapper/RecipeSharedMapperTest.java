package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeSharedMapperTest {

    private RecipeSharedMapper recipeSharedMapper;

    @BeforeEach
    public void setUp() {
        recipeSharedMapper = new RecipeSharedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipeSharedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipeSharedMapper.fromId(null)).isNull();
    }
}
