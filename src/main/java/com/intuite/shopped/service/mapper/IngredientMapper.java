package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {

    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.abbrev", target = "unitAbbrev")
    IngredientDTO toDto(Ingredient ingredient);

    @Mapping(source = "unitId", target = "unit")
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
