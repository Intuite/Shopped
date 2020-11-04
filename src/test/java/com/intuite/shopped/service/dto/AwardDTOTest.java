package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class AwardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AwardDTO.class);
        AwardDTO awardDTO1 = new AwardDTO();
        awardDTO1.setId(1L);
        AwardDTO awardDTO2 = new AwardDTO();
        assertThat(awardDTO1).isNotEqualTo(awardDTO2);
        awardDTO2.setId(awardDTO1.getId());
        assertThat(awardDTO1).isEqualTo(awardDTO2);
        awardDTO2.setId(2L);
        assertThat(awardDTO1).isNotEqualTo(awardDTO2);
        awardDTO1.setId(null);
        assertThat(awardDTO1).isNotEqualTo(awardDTO2);
    }
}
