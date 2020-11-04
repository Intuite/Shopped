package com.intuite.shopped.repository;

import com.intuite.shopped.domain.IngredientTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IngredientTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientTagRepository extends JpaRepository<IngredientTag, Long>, JpaSpecificationExecutor<IngredientTag> {
}
