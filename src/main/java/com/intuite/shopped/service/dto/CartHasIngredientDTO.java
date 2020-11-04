package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.CartHasIngredient} entity.
 */
@ApiModel(description = "Intermediate entity\nfor cart and\ningredients.\n@author Isaac Miranda")
public class CartHasIngredientDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer amount;

    private Status status;


    private Long cartId;

    private Long ingredientId;

    private String ingredientName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartHasIngredientDTO)) {
            return false;
        }

        return id != null && id.equals(((CartHasIngredientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartHasIngredientDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", cartId=" + getCartId() +
            ", ingredientId=" + getIngredientId() +
            ", ingredientName='" + getIngredientName() + "'" +
            "}";
    }
}
