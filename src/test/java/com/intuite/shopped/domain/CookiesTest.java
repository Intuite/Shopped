package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CookiesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cookies.class);
        Cookies cookies1 = new Cookies();
        cookies1.setId(1L);
        Cookies cookies2 = new Cookies();
        cookies2.setId(cookies1.getId());
        assertThat(cookies1).isEqualTo(cookies2);
        cookies2.setId(2L);
        assertThat(cookies1).isNotEqualTo(cookies2);
        cookies1.setId(null);
        assertThat(cookies1).isNotEqualTo(cookies2);
    }
}
