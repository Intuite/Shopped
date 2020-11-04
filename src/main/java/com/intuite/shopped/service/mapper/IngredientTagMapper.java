package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.IngredientTagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngredientTag} and its DTO {@link IngredientTagDTO}.
 */
@Mapper(componentModel = "spring", uses = {TagTypeMapper.class})
public interface IngredientTagMapper extends EntityMapper<IngredientTagDTO, IngredientTag> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    IngredientTagDTO toDto(IngredientTag ingredientTag);

    @Mapping(source = "typeId", target = "type")
    IngredientTag toEntity(IngredientTagDTO ingredientTagDTO);

    default IngredientTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientTag ingredientTag = new IngredientTag();
        ingredientTag.setId(id);
        return ingredientTag;
    }
}
