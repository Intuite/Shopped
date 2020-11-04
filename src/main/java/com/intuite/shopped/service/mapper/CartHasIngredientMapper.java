package com.intuite.shopped.service.mapper;


import com.intuite.shopped.domain.*;
import com.intuite.shopped.service.dto.CartHasIngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartHasIngredient} and its DTO {@link CartHasIngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {CartMapper.class, IngredientMapper.class})
public interface CartHasIngredientMapper extends EntityMapper<CartHasIngredientDTO, CartHasIngredient> {

    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.name", target = "ingredientName")
    CartHasIngredientDTO toDto(CartHasIngredient cartHasIngredient);

    @Mapping(source = "cartId", target = "cart")
    @Mapping(source = "ingredientId", target = "ingredient")
    CartHasIngredient toEntity(CartHasIngredientDTO cartHasIngredientDTO);

    default CartHasIngredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        CartHasIngredient cartHasIngredient = new CartHasIngredient();
        cartHasIngredient.setId(id);
        return cartHasIngredient;
    }
}
