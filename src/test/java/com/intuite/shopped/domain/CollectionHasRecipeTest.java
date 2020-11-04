package com.intuite.shopped.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.intuite.shopped.web.rest.TestUtil;

public class CollectionHasRecipeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionHasRecipe.class);
        CollectionHasRecipe collectionHasRecipe1 = new CollectionHasRecipe();
        collectionHasRecipe1.setId(1L);
        CollectionHasRecipe collectionHasRecipe2 = new CollectionHasRecipe();
        collectionHasRecipe2.setId(collectionHasRecipe1.getId());
        assertThat(collectionHasRecipe1).isEqualTo(collectionHasRecipe2);
        collectionHasRecipe2.setId(2L);
        assertThat(collectionHasRecipe1).isNotEqualTo(collectionHasRecipe2);
        collectionHasRecipe1.setId(null);
        assertThat(collectionHasRecipe1).isNotEqualTo(collectionHasRecipe2);
    }
}
