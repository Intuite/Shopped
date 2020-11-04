package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BiteMapperTest {

    private BiteMapper biteMapper;

    @BeforeEach
    public void setUp() {
        biteMapper = new BiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(biteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(biteMapper.fromId(null)).isNull();
    }
}
