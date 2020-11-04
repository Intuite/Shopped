package com.intuite.shopped.repository;

import com.intuite.shopped.domain.IngredientHasIngredientTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IngredientHasIngredientTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientHasIngredientTagRepository extends JpaRepository<IngredientHasIngredientTag, Long>, JpaSpecificationExecutor<IngredientHasIngredientTag> {
}
