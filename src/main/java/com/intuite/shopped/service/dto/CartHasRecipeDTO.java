package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.CartHasRecipe} entity.
 */
@ApiModel(description = "Intermediate entity\nfor recipes and carts.\n@author Isaac Miranda")
public class CartHasRecipeDTO implements Serializable {
    
    private Long id;

    private Status status;


    private Long recipeId;

    private String recipeName;

    private Long cartId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartHasRecipeDTO)) {
            return false;
        }

        return id != null && id.equals(((CartHasRecipeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartHasRecipeDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", recipeId=" + getRecipeId() +
            ", recipeName='" + getRecipeName() + "'" +
            ", cartId=" + getCartId() +
            "}";
    }
}
