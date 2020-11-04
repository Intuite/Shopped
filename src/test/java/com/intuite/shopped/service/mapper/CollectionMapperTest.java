package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionMapperTest {

    private CollectionMapper collectionMapper;

    @BeforeEach
    public void setUp() {
        collectionMapper = new CollectionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(collectionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(collectionMapper.fromId(null)).isNull();
    }
}
