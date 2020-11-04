package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AwardMapperTest {

    private AwardMapper awardMapper;

    @BeforeEach
    public void setUp() {
        awardMapper = new AwardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(awardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(awardMapper.fromId(null)).isNull();
    }
}
