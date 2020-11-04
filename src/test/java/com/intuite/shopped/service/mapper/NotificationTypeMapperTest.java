package com.intuite.shopped.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationTypeMapperTest {

    private NotificationTypeMapper notificationTypeMapper;

    @BeforeEach
    public void setUp() {
        notificationTypeMapper = new NotificationTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificationTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificationTypeMapper.fromId(null)).isNull();
    }
}
