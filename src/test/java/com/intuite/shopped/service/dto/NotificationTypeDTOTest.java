package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class NotificationTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationTypeDTO.class);
        NotificationTypeDTO notificationTypeDTO1 = new NotificationTypeDTO();
        notificationTypeDTO1.setId(1L);
        NotificationTypeDTO notificationTypeDTO2 = new NotificationTypeDTO();
        assertThat(notificationTypeDTO1).isNotEqualTo(notificationTypeDTO2);
        notificationTypeDTO2.setId(notificationTypeDTO1.getId());
        assertThat(notificationTypeDTO1).isEqualTo(notificationTypeDTO2);
        notificationTypeDTO2.setId(2L);
        assertThat(notificationTypeDTO1).isNotEqualTo(notificationTypeDTO2);
        notificationTypeDTO1.setId(null);
        assertThat(notificationTypeDTO1).isNotEqualTo(notificationTypeDTO2);
    }
}
