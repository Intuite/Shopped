package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CommendationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommendationDTO.class);
        CommendationDTO commendationDTO1 = new CommendationDTO();
        commendationDTO1.setId(1L);
        CommendationDTO commendationDTO2 = new CommendationDTO();
        assertThat(commendationDTO1).isNotEqualTo(commendationDTO2);
        commendationDTO2.setId(commendationDTO1.getId());
        assertThat(commendationDTO1).isEqualTo(commendationDTO2);
        commendationDTO2.setId(2L);
        assertThat(commendationDTO1).isNotEqualTo(commendationDTO2);
        commendationDTO1.setId(null);
        assertThat(commendationDTO1).isNotEqualTo(commendationDTO2);
    }
}
