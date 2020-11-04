package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportCommentMapperTest {

    private ReportCommentMapper reportCommentMapper;

    @BeforeEach
    public void setUp() {
        reportCommentMapper = new ReportCommentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reportCommentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reportCommentMapper.fromId(null)).isNull();
    }
}
