package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CartHasRecipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartHasRecipe} and its DTO {@link CartHasRecipeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class, CartMapper.class})
public interface CartHasRecipeMapper extends EntityMapper<CartHasRecipeDTO, CartHasRecipe> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.name", target = "recipeName")
    @Mapping(source = "cart.id", target = "cartId")
    CartHasRecipeDTO toDto(CartHasRecipe cartHasRecipe);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(source = "cartId", target = "cart")
    CartHasRecipe toEntity(CartHasRecipeDTO cartHasRecipeDTO);

    default CartHasRecipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        CartHasRecipe cartHasRecipe = new CartHasRecipe();
        cartHasRecipe.setId(id);
        return cartHasRecipe;
    }
}
