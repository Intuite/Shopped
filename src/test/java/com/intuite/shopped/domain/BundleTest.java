package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class BundleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bundle.class);
        Bundle bundle1 = new Bundle();
        bundle1.setId(1L);
        Bundle bundle2 = new Bundle();
        bundle2.setId(bundle1.getId());
        assertThat(bundle1).isEqualTo(bundle2);
        bundle2.setId(2L);
        assertThat(bundle1).isNotEqualTo(bundle2);
        bundle1.setId(null);
        assertThat(bundle1).isNotEqualTo(bundle2);
    }
}
