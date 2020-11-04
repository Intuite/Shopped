package com.intuite.shopped.repository;

import com.intuite.shopped.domain.CartHasRecipe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CartHasRecipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartHasRecipeRepository extends JpaRepository<CartHasRecipe, Long>, JpaSpecificationExecutor<CartHasRecipe> {
}
