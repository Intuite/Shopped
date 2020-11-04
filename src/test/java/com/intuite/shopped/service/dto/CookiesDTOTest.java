package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CookiesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CookiesDTO.class);
        CookiesDTO cookiesDTO1 = new CookiesDTO();
        cookiesDTO1.setId(1L);
        CookiesDTO cookiesDTO2 = new CookiesDTO();
        assertThat(cookiesDTO1).isNotEqualTo(cookiesDTO2);
        cookiesDTO2.setId(cookiesDTO1.getId());
        assertThat(cookiesDTO1).isEqualTo(cookiesDTO2);
        cookiesDTO2.setId(2L);
        assertThat(cookiesDTO1).isNotEqualTo(cookiesDTO2);
        cookiesDTO1.setId(null);
        assertThat(cookiesDTO1).isNotEqualTo(cookiesDTO2);
    }
}
