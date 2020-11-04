package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportCommentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportCommentDTO.class);
        ReportCommentDTO reportCommentDTO1 = new ReportCommentDTO();
        reportCommentDTO1.setId(1L);
        ReportCommentDTO reportCommentDTO2 = new ReportCommentDTO();
        assertThat(reportCommentDTO1).isNotEqualTo(reportCommentDTO2);
        reportCommentDTO2.setId(reportCommentDTO1.getId());
        assertThat(reportCommentDTO1).isEqualTo(reportCommentDTO2);
        reportCommentDTO2.setId(2L);
        assertThat(reportCommentDTO1).isNotEqualTo(reportCommentDTO2);
        reportCommentDTO1.setId(null);
        assertThat(reportCommentDTO1).isNotEqualTo(reportCommentDTO2);
    }
}
