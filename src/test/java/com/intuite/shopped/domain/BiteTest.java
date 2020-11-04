package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class BiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bite.class);
        Bite bite1 = new Bite();
        bite1.setId(1L);
        Bite bite2 = new Bite();
        bite2.setId(bite1.getId());
        assertThat(bite1).isEqualTo(bite2);
        bite2.setId(2L);
        assertThat(bite1).isNotEqualTo(bite2);
        bite1.setId(null);
        assertThat(bite1).isNotEqualTo(bite2);
    }
}
