package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionHasRecipeMapperTest {

    private CollectionHasRecipeMapper collectionHasRecipeMapper;

    @BeforeEach
    public void setUp() {
        collectionHasRecipeMapper = new CollectionHasRecipeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(collectionHasRecipeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(collectionHasRecipeMapper.fromId(null)).isNull();
    }
}
