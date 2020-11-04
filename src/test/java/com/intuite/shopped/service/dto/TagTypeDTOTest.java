package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class TagTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagTypeDTO.class);
        TagTypeDTO tagTypeDTO1 = new TagTypeDTO();
        tagTypeDTO1.setId(1L);
        TagTypeDTO tagTypeDTO2 = new TagTypeDTO();
        assertThat(tagTypeDTO1).isNotEqualTo(tagTypeDTO2);
        tagTypeDTO2.setId(tagTypeDTO1.getId());
        assertThat(tagTypeDTO1).isEqualTo(tagTypeDTO2);
        tagTypeDTO2.setId(2L);
        assertThat(tagTypeDTO1).isNotEqualTo(tagTypeDTO2);
        tagTypeDTO1.setId(null);
        assertThat(tagTypeDTO1).isNotEqualTo(tagTypeDTO2);
    }
}
