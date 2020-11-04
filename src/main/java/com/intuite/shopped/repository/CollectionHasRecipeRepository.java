package com.intuite.shopped.repository;

import com.intuite.shopped.domain.CollectionHasRecipe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CollectionHasRecipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectionHasRecipeRepository extends JpaRepository<CollectionHasRecipe, Long>, JpaSpecificationExecutor<CollectionHasRecipe> {
}
