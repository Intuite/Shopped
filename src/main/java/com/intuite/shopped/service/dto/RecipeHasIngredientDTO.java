package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.RecipeHasIngredient} entity.
 */
@ApiModel(description = "Intermediate entity\nfor ingredients and\nrecipes.\n@author Isaac Miranda")
public class RecipeHasIngredientDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer amount;

    private Status status;


    private Long ingredientId;

    private String ingredientName;

    private Long recipeId;

    private String recipeName;
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeHasIngredientDTO)) {
            return false;
        }

        return id != null && id.equals(((RecipeHasIngredientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeHasIngredientDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", ingredientId=" + getIngredientId() +
            ", ingredientName='" + getIngredientName() + "'" +
            ", recipeId=" + getRecipeId() +
            ", recipeName='" + getRecipeName() + "'" +
            "}";
    }
}
