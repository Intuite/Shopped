package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CollectionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionDTO.class);
        CollectionDTO collectionDTO1 = new CollectionDTO();
        collectionDTO1.setId(1L);
        CollectionDTO collectionDTO2 = new CollectionDTO();
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
        collectionDTO2.setId(collectionDTO1.getId());
        assertThat(collectionDTO1).isEqualTo(collectionDTO2);
        collectionDTO2.setId(2L);
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
        collectionDTO1.setId(null);
        assertThat(collectionDTO1).isNotEqualTo(collectionDTO2);
    }
}
