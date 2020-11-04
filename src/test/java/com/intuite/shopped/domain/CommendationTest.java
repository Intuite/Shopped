package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CommendationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commendation.class);
        Commendation commendation1 = new Commendation();
        commendation1.setId(1L);
        Commendation commendation2 = new Commendation();
        commendation2.setId(commendation1.getId());
        assertThat(commendation1).isEqualTo(commendation2);
        commendation2.setId(2L);
        assertThat(commendation1).isNotEqualTo(commendation2);
        commendation1.setId(null);
        assertThat(commendation1).isNotEqualTo(commendation2);
    }
}
