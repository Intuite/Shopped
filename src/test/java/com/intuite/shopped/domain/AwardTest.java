package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class AwardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Award.class);
        Award award1 = new Award();
        award1.setId(1L);
        Award award2 = new Award();
        award2.setId(award1.getId());
        assertThat(award1).isEqualTo(award2);
        award2.setId(2L);
        assertThat(award1).isNotEqualTo(award2);
        award1.setId(null);
        assertThat(award1).isNotEqualTo(award2);
    }
}
