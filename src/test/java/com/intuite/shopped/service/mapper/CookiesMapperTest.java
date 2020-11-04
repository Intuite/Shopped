package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CookiesMapperTest {

    private CookiesMapper cookiesMapper;

    @BeforeEach
    public void setUp() {
        cookiesMapper = new CookiesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cookiesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cookiesMapper.fromId(null)).isNull();
    }
}
