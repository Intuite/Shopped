package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FollowerMapperTest {

    private FollowerMapper followerMapper;

    @BeforeEach
    public void setUp() {
        followerMapper = new FollowerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(followerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(followerMapper.fromId(null)).isNull();
    }
}
