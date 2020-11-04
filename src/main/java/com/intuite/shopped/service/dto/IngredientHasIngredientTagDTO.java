package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.IngredientHasIngredientTag} entity.
 */
@ApiModel(description = "Intermediate entity\nfor ingredient tags\nand ingredients.\n@author Isaac Miranda")
public class IngredientHasIngredientTagDTO implements Serializable {
    
    private Long id;

    private Status status;


    private Long ingredientId;

    private String ingredientName;

    private Long ingredientTagId;

    private String ingredientTagName;
    
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

    public Long getIngredientTagId() {
        return ingredientTagId;
    }

    public void setIngredientTagId(Long ingredientTagId) {
        this.ingredientTagId = ingredientTagId;
    }

    public String getIngredientTagName() {
        return ingredientTagName;
    }

    public void setIngredientTagName(String ingredientTagName) {
        this.ingredientTagName = ingredientTagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientHasIngredientTagDTO)) {
            return false;
        }

        return id != null && id.equals(((IngredientHasIngredientTagDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientHasIngredientTagDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", ingredientId=" + getIngredientId() +
            ", ingredientName='" + getIngredientName() + "'" +
            ", ingredientTagId=" + getIngredientTagId() +
            ", ingredientTagName='" + getIngredientTagName() + "'" +
            "}";
    }
}
