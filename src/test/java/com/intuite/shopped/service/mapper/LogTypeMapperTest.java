package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogTypeMapperTest {

    private LogTypeMapper logTypeMapper;

    @BeforeEach
    public void setUp() {
        logTypeMapper = new LogTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(logTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(logTypeMapper.fromId(null)).isNull();
    }
}
