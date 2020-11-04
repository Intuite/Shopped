package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.intuite.shopped.domain.enumeration.Status;

/**
 * Intermediate entity\nfor collectios and\nrecipes.\n@author Isaac Miranda
 */
@Entity
@Table(name = "collection_has_recipe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollectionHasRecipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "collectionHasRecipes", allowSetters = true)
    private Collection collection;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "collectionHasRecipes", allowSetters = true)
    private Recipe recipe;

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

    public CollectionHasRecipe status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection getCollection() {
        return collection;
    }

    public CollectionHasRecipe collection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public CollectionHasRecipe recipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionHasRecipe)) {
            return false;
        }
        return id != null && id.equals(((CollectionHasRecipe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectionHasRecipe{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
