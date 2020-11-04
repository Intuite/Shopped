package com.intuite.shopped.repository;

import com.intuite.shopped.domain.RecipeHasRecipeTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecipeHasRecipeTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeHasRecipeTagRepository extends JpaRepository<RecipeHasRecipeTag, Long>, JpaSpecificationExecutor<RecipeHasRecipeTag> {
}
