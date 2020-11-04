package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportTypeMapperTest {

    private ReportTypeMapper reportTypeMapper;

    @BeforeEach
    public void setUp() {
        reportTypeMapper = new ReportTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportTypeMapper.fromId(null)).isNull();
    }
}
