package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeHasRecipeTag} and its DTO {@link RecipeHasRecipeTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class, RecipeTagMapper.class})
public interface RecipeHasRecipeTagMapper extends EntityMapper<RecipeHasRecipeTagDTO, RecipeHasRecipeTag> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.name", target = "recipeName")
    @Mapping(source = "recipeTag.id", target = "recipeTagId")
    @Mapping(source = "recipeTag.name", target = "recipeTagName")
    RecipeHasRecipeTagDTO toDto(RecipeHasRecipeTag recipeHasRecipeTag);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(source = "recipeTagId", target = "recipeTag")
    RecipeHasRecipeTag toEntity(RecipeHasRecipeTagDTO recipeHasRecipeTagDTO);

    default RecipeHasRecipeTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeHasRecipeTag recipeHasRecipeTag = new RecipeHasRecipeTag();
        recipeHasRecipeTag.setId(id);
        return recipeHasRecipeTag;
    }
}
