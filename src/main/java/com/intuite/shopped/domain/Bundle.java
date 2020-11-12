package com.intuite.shopped.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Bundle.
 */
@Entity
@Table(name = "bundle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bundle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "cost", nullable = false)
    private Double cost;

    @NotNull
    @Min(value = 0)
    @Column(name = "cookie_amount", nullable = false)
    private Integer cookieAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Bundle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public Bundle cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getCookieAmount() {
        return cookieAmount;
    }

    public Bundle cookieAmount(Integer cookieAmount) {
        this.cookieAmount = cookieAmount;
        return this;
    }

    public void setCookieAmount(Integer cookieAmount) {
        this.cookieAmount = cookieAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bundle)) {
            return false;
        }
        return id != null && id.equals(((Bundle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bundle{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cost=" + getCost() +
            ", cookieAmount=" + getCookieAmount() +
            "}";
    }
}
