package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngredientHasIngredientTag} and its DTO {@link IngredientHasIngredientTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class, IngredientTagMapper.class})
public interface IngredientHasIngredientTagMapper extends EntityMapper<IngredientHasIngredientTagDTO, IngredientHasIngredientTag> {

    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    @Mapping(source = "ingredientTag.id", target = "ingredientTagId")
    @Mapping(source = "ingredientTag.name", target = "ingredientTagName")
    IngredientHasIngredientTagDTO toDto(IngredientHasIngredientTag ingredientHasIngredientTag);

    @Mapping(source = "ingredientId", target = "ingredient")
    @Mapping(source = "ingredientTagId", target = "ingredientTag")
    IngredientHasIngredientTag toEntity(IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO);

    default IngredientHasIngredientTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientHasIngredientTag ingredientHasIngredientTag = new IngredientHasIngredientTag();
        ingredientHasIngredientTag.setId(id);
        return ingredientHasIngredientTag;
    }
}
