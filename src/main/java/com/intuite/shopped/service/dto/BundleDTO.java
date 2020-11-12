package com.intuite.shopped.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Bundle} entity.
 */
public class BundleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    private Double cost;

    @NotNull
    @Min(value = 0)
    private Integer cookieAmount;

    
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getCookieAmount() {
        return cookieAmount;
    }

    public void setCookieAmount(Integer cookieAmount) {
        this.cookieAmount = cookieAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BundleDTO)) {
            return false;
        }

        return id != null && id.equals(((BundleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BundleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cost=" + getCost() +
            ", cookieAmount=" + getCookieAmount() +
            "}";
    }
}
