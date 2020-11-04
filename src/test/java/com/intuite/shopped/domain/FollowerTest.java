package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class FollowerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follower.class);
        Follower follower1 = new Follower();
        follower1.setId(1L);
        Follower follower2 = new Follower();
        follower2.setId(follower1.getId());
        assertThat(follower1).isEqualTo(follower2);
        follower2.setId(2L);
        assertThat(follower1).isNotEqualTo(follower2);
        follower1.setId(null);
        assertThat(follower1).isNotEqualTo(follower2);
    }
}
