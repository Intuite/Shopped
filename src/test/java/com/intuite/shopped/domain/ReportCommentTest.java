package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportComment.class);
        ReportComment reportComment1 = new ReportComment();
        reportComment1.setId(1L);
        ReportComment reportComment2 = new ReportComment();
        reportComment2.setId(reportComment1.getId());
        assertThat(reportComment1).isEqualTo(reportComment2);
        reportComment2.setId(2L);
        assertThat(reportComment1).isNotEqualTo(reportComment2);
        reportComment1.setId(null);
        assertThat(reportComment1).isNotEqualTo(reportComment2);
    }
}
