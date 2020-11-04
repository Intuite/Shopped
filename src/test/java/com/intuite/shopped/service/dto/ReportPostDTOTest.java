package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportPostDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportPostDTO.class);
        ReportPostDTO reportPostDTO1 = new ReportPostDTO();
        reportPostDTO1.setId(1L);
        ReportPostDTO reportPostDTO2 = new ReportPostDTO();
        assertThat(reportPostDTO1).isNotEqualTo(reportPostDTO2);
        reportPostDTO2.setId(reportPostDTO1.getId());
        assertThat(reportPostDTO1).isEqualTo(reportPostDTO2);
        reportPostDTO2.setId(2L);
        assertThat(reportPostDTO1).isNotEqualTo(reportPostDTO2);
        reportPostDTO1.setId(null);
        assertThat(reportPostDTO1).isNotEqualTo(reportPostDTO2);
    }
}
