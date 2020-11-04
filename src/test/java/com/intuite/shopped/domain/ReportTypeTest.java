package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportType.class);
        ReportType reportType1 = new ReportType();
        reportType1.setId(1L);
        ReportType reportType2 = new ReportType();
        reportType2.setId(reportType1.getId());
        assertThat(reportType1).isEqualTo(reportType2);
        reportType2.setId(2L);
        assertThat(reportType1).isNotEqualTo(reportType2);
        reportType1.setId(null);
        assertThat(reportType1).isNotEqualTo(reportType2);
    }
}
