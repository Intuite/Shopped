package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TagTypeMapperTest {

    private TagTypeMapper tagTypeMapper;

    @BeforeEach
    public void setUp() {
        tagTypeMapper = new TagTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tagTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tagTypeMapper.fromId(null)).isNull();
    }
}
