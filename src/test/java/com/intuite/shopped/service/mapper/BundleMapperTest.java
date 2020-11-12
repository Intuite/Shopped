package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BundleMapperTest {

    private BundleMapper bundleMapper;

    @BeforeEach
    public void setUp() {
        bundleMapper = new BundleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bundleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bundleMapper.fromId(null)).isNull();
    }
}
