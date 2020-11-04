package com.intuite.shopped.repository;

import com.intuite.shopped.domain.RecipeShared;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RecipeShared entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeSharedRepository extends JpaRepository<RecipeShared, Long>, JpaSpecificationExecutor<RecipeShared> {

    @Query("select recipeShared from RecipeShared recipeShared where recipeShared.user.login = ?#{principal.username}")
    List<RecipeShared> findByUserIsCurrentUser();
}
