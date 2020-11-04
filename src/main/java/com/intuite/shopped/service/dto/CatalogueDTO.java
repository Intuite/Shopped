package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Catalogue} entity.
 */
@ApiModel(description = "Extra catalogues for\nmultiple functions.\n@author Isaac Miranda")
public class CatalogueDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String idCatalogue;

    @NotNull
    private String value;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCatalogue() {
        return idCatalogue;
    }

    public void setIdCatalogue(String idCatalogue) {
        this.idCatalogue = idCatalogue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogueDTO)) {
            return false;
        }

        return id != null && id.equals(((CatalogueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueDTO{" +
            "id=" + getId() +
            ", idCatalogue='" + getIdCatalogue() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
