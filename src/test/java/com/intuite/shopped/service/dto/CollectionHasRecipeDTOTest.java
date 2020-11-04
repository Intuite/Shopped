package com.intuite.shopped.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CollectionHasRecipeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionHasRecipeDTO.class);
        CollectionHasRecipeDTO collectionHasRecipeDTO1 = new CollectionHasRecipeDTO();
        collectionHasRecipeDTO1.setId(1L);
        CollectionHasRecipeDTO collectionHasRecipeDTO2 = new CollectionHasRecipeDTO();
        assertThat(collectionHasRecipeDTO1).isNotEqualTo(collectionHasRecipeDTO2);
        collectionHasRecipeDTO2.setId(collectionHasRecipeDTO1.getId());
        assertThat(collectionHasRecipeDTO1).isEqualTo(collectionHasRecipeDTO2);
        collectionHasRecipeDTO2.setId(2L);
        assertThat(collectionHasRecipeDTO1).isNotEqualTo(collectionHasRecipeDTO2);
        collectionHasRecipeDTO1.setId(null);
        assertThat(collectionHasRecipeDTO1).isNotEqualTo(collectionHasRecipeDTO2);
    }
}
