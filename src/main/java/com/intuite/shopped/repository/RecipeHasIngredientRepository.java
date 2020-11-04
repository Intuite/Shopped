package com.intuite.shopped.repository;

import com.intuite.shopped.domain.RecipeHasIngredient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecipeHasIngredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeHasIngredientRepository extends JpaRepository<RecipeHasIngredient, Long>, JpaSpecificationExecutor<RecipeHasIngredient> {
}
