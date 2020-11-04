package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.RecipeHasRecipeTag} entity.
 */
@ApiModel(description = "Intermediate entity\nfor recipes and recipe\ntags.\n@author Isaac Miranda")
public class RecipeHasRecipeTagDTO implements Serializable {
    
    private Long id;

    private Status status;


    private Long recipeId;

    private String recipeName;

    private Long recipeTagId;

    private String recipeTagName;
    
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

    public Long getRecipeTagId() {
        return recipeTagId;
    }

    public void setRecipeTagId(Long recipeTagId) {
        this.recipeTagId = recipeTagId;
    }

    public String getRecipeTagName() {
        return recipeTagName;
    }

    public void setRecipeTagName(String recipeTagName) {
        this.recipeTagName = recipeTagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeHasRecipeTagDTO)) {
            return false;
        }

        return id != null && id.equals(((RecipeHasRecipeTagDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeHasRecipeTagDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", recipeId=" + getRecipeId() +
            ", recipeName='" + getRecipeName() + "'" +
            ", recipeTagId=" + getRecipeTagId() +
            ", recipeTagName='" + getRecipeTagName() + "'" +
            "}";
    }
}
