package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class BundleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BundleDTO.class);
        BundleDTO bundleDTO1 = new BundleDTO();
        bundleDTO1.setId(1L);
        BundleDTO bundleDTO2 = new BundleDTO();
        assertThat(bundleDTO1).isNotEqualTo(bundleDTO2);
        bundleDTO2.setId(bundleDTO1.getId());
        assertThat(bundleDTO1).isEqualTo(bundleDTO2);
        bundleDTO2.setId(2L);
        assertThat(bundleDTO1).isNotEqualTo(bundleDTO2);
        bundleDTO1.setId(null);
        assertThat(bundleDTO1).isNotEqualTo(bundleDTO2);
    }
}
