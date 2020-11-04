package com.intuite.shopped.repository;

import com.intuite.shopped.domain.RecipeTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecipeTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long>, JpaSpecificationExecutor<RecipeTag> {
}
