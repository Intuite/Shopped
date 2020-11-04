package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.intuite.shopped.domain.enumeration.Status;

/**
 * Intermediate entity\nfor ingredient tags\nand ingredients.\n@author Isaac Miranda
 */
@Entity
@Table(name = "ingredient_has_ingredient_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IngredientHasIngredientTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ingredientHasIngredientTags", allowSetters = true)
    private Ingredient ingredient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ingredientHasIngredientTags", allowSetters = true)
    private IngredientTag ingredientTag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public IngredientHasIngredientTag status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public IngredientHasIngredientTag ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public IngredientTag getIngredientTag() {
        return ingredientTag;
    }

    public IngredientHasIngredientTag ingredientTag(IngredientTag ingredientTag) {
        this.ingredientTag = ingredientTag;
        return this;
    }

    public void setIngredientTag(IngredientTag ingredientTag) {
        this.ingredientTag = ingredientTag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientHasIngredientTag)) {
            return false;
        }
        return id != null && id.equals(((IngredientHasIngredientTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientHasIngredientTag{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
