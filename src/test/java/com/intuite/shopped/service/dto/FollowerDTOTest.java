package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class FollowerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowerDTO.class);
        FollowerDTO followerDTO1 = new FollowerDTO();
        followerDTO1.setId(1L);
        FollowerDTO followerDTO2 = new FollowerDTO();
        assertThat(followerDTO1).isNotEqualTo(followerDTO2);
        followerDTO2.setId(followerDTO1.getId());
        assertThat(followerDTO1).isEqualTo(followerDTO2);
        followerDTO2.setId(2L);
        assertThat(followerDTO1).isNotEqualTo(followerDTO2);
        followerDTO1.setId(null);
        assertThat(followerDTO1).isNotEqualTo(followerDTO2);
    }
}
