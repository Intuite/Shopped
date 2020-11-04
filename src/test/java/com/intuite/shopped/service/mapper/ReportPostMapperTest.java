package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportPostMapperTest {

    private ReportPostMapper reportPostMapper;

    @BeforeEach
    public void setUp() {
        reportPostMapper = new ReportPostMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportPostMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportPostMapper.fromId(null)).isNull();
    }
}
