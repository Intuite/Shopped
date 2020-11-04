package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class BiteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiteDTO.class);
        BiteDTO biteDTO1 = new BiteDTO();
        biteDTO1.setId(1L);
        BiteDTO biteDTO2 = new BiteDTO();
        assertThat(biteDTO1).isNotEqualTo(biteDTO2);
        biteDTO2.setId(biteDTO1.getId());
        assertThat(biteDTO1).isEqualTo(biteDTO2);
        biteDTO2.setId(2L);
        assertThat(biteDTO1).isNotEqualTo(biteDTO2);
        biteDTO1.setId(null);
        assertThat(biteDTO1).isNotEqualTo(biteDTO2);
    }
}
