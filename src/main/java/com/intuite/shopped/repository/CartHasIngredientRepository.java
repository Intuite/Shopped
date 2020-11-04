package com.intuite.shopped.repository;

import com.intuite.shopped.domain.CartHasIngredient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CartHasIngredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartHasIngredientRepository extends JpaRepository<CartHasIngredient, Long>, JpaSpecificationExecutor<CartHasIngredient> {
}
