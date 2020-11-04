package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.RecipeTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeTag} and its DTO {@link RecipeTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {TagTypeMapper.class})
public interface RecipeTagMapper extends EntityMapper<RecipeTagDTO, RecipeTag> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    RecipeTagDTO toDto(RecipeTag recipeTag);

    @Mapping(source = "typeId", target = "type")
    RecipeTag toEntity(RecipeTagDTO recipeTagDTO);

    default RecipeTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeTag recipeTag = new RecipeTag();
        recipeTag.setId(id);
        return recipeTag;
    }
}
