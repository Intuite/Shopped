package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportTypeDTO.class);
        ReportTypeDTO reportTypeDTO1 = new ReportTypeDTO();
        reportTypeDTO1.setId(1L);
        ReportTypeDTO reportTypeDTO2 = new ReportTypeDTO();
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
        reportTypeDTO2.setId(reportTypeDTO1.getId());
        assertThat(reportTypeDTO1).isEqualTo(reportTypeDTO2);
        reportTypeDTO2.setId(2L);
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
        reportTypeDTO1.setId(null);
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
    }
}
