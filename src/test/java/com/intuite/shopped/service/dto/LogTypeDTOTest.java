package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class LogTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogTypeDTO.class);
        LogTypeDTO logTypeDTO1 = new LogTypeDTO();
        logTypeDTO1.setId(1L);
        LogTypeDTO logTypeDTO2 = new LogTypeDTO();
        assertThat(logTypeDTO1).isNotEqualTo(logTypeDTO2);
        logTypeDTO2.setId(logTypeDTO1.getId());
        assertThat(logTypeDTO1).isEqualTo(logTypeDTO2);
        logTypeDTO2.setId(2L);
        assertThat(logTypeDTO1).isNotEqualTo(logTypeDTO2);
        logTypeDTO1.setId(null);
        assertThat(logTypeDTO1).isNotEqualTo(logTypeDTO2);
    }
}
