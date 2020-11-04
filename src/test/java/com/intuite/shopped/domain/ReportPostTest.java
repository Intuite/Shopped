package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class ReportPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportPost.class);
        ReportPost reportPost1 = new ReportPost();
        reportPost1.setId(1L);
        ReportPost reportPost2 = new ReportPost();
        reportPost2.setId(reportPost1.getId());
        assertThat(reportPost1).isEqualTo(reportPost2);
        reportPost2.setId(2L);
        assertThat(reportPost1).isNotEqualTo(reportPost2);
        reportPost1.setId(null);
        assertThat(reportPost1).isNotEqualTo(reportPost2);
    }
}
