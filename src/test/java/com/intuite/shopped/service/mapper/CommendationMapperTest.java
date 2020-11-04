package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommendationMapperTest {

    private CommendationMapper commendationMapper;

    @BeforeEach
    public void setUp() {
        commendationMapper = new CommendationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commendationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commendationMapper.fromId(null)).isNull();
    }
}
