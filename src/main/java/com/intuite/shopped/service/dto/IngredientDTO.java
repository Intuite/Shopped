package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Ingredient} entity.
 */
@ApiModel(description = "Ingredients for\nall recipes.\n@author Isaac Miranda")
public class IngredientDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Status status;


    private Long unitId;

    private String unitAbbrev;
    
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitAbbrev() {
        return unitAbbrev;
    }

    public void setUnitAbbrev(String unitAbbrev) {
        this.unitAbbrev = unitAbbrev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientDTO)) {
            return false;
        }

        return id != null && id.equals(((IngredientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", status='" + getStatus() + "'" +
            ", unitId=" + getUnitId() +
            ", unitAbbrev='" + getUnitAbbrev() + "'" +
            "}";
    }
}
