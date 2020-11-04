package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.CollectionHasRecipe} entity.
 */
@ApiModel(description = "Intermediate entity\nfor collectios and\nrecipes.\n@author Isaac Miranda")
public class CollectionHasRecipeDTO implements Serializable {
    
    private Long id;

    private Status status;


    private Long collectionId;

    private String collectionName;

    private Long recipeId;

    private String recipeName;
    
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

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
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
        if (!(o instanceof CollectionHasRecipeDTO)) {
            return false;
        }

        return id != null && id.equals(((CollectionHasRecipeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectionHasRecipeDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", collectionId=" + getCollectionId() +
            ", collectionName='" + getCollectionName() + "'" +
            ", recipeId=" + getRecipeId() +
            ", recipeName='" + getRecipeName() + "'" +
            "}";
    }
}
