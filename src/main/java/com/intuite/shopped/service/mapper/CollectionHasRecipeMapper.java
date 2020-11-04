package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CollectionHasRecipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CollectionHasRecipe} and its DTO {@link CollectionHasRecipeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CollectionMapper.class, RecipeMapper.class})
public interface CollectionHasRecipeMapper extends EntityMapper<CollectionHasRecipeDTO, CollectionHasRecipe> {

    @Mapping(source = "collection.id", target = "collectionId")
    @Mapping(source = "collection.name", target = "collectionName")
    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.name", target = "recipeName")
    CollectionHasRecipeDTO toDto(CollectionHasRecipe collectionHasRecipe);

    @Mapping(source = "collectionId", target = "collection")
    @Mapping(source = "recipeId", target = "recipe")
    CollectionHasRecipe toEntity(CollectionHasRecipeDTO collectionHasRecipeDTO);

    default CollectionHasRecipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        CollectionHasRecipe collectionHasRecipe = new CollectionHasRecipe();
        collectionHasRecipe.setId(id);
        return collectionHasRecipe;
    }
}
