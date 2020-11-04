package com.intuite.shopped.repository;

import com.intuite.shopped.domain.Recipe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Recipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    @Query("select recipe from Recipe recipe where recipe.user.login = ?#{principal.username}")
    List<Recipe> findByUserIsCurrentUser();
}
