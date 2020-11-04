package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.RecipeHasIngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeHasIngredient} and its DTO {@link RecipeHasIngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {IngredientMapper.class, RecipeMapper.class})
public interface RecipeHasIngredientMapper extends EntityMapper<RecipeHasIngredientDTO, RecipeHasIngredient> {

    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.name", target = "recipeName")
    RecipeHasIngredientDTO toDto(RecipeHasIngredient recipeHasIngredient);

    @Mapping(source = "ingredientId", target = "ingredient")
    @Mapping(source = "recipeId", target = "recipe")
    RecipeHasIngredient toEntity(RecipeHasIngredientDTO recipeHasIngredientDTO);

    default RecipeHasIngredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();
        recipeHasIngredient.setId(id);
        return recipeHasIngredient;
    }
}
