package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.IngredientTag} entity.
 */
@ApiModel(description = "Tags for ingredients.\n@author Isaac Miranda")
public class IngredientTagDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Status status;


    private Long typeId;

    private String typeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long tagTypeId) {
        this.typeId = tagTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String tagTypeName) {
        this.typeName = tagTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientTagDTO)) {
            return false;
        }

        return id != null && id.equals(((IngredientTagDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientTagDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            "}";
    }
}
