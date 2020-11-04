package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.RecipeSharedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeShared} and its DTO {@link RecipeSharedDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class, UserMapper.class})
public interface RecipeSharedMapper extends EntityMapper<RecipeSharedDTO, RecipeShared> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.name", target = "recipeName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    RecipeSharedDTO toDto(RecipeShared recipeShared);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(source = "userId", target = "user")
    RecipeShared toEntity(RecipeSharedDTO recipeSharedDTO);

    default RecipeShared fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeShared recipeShared = new RecipeShared();
        recipeShared.setId(id);
        return recipeShared;
    }
}
