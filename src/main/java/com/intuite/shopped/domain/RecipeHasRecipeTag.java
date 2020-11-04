package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.intuite.shopped.domain.enumeration.Status;

/**
 * Intermediate entity\nfor recipes and recipe\ntags.\n@author Isaac Miranda
 */
@Entity
@Table(name = "recipe_has_recipe_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecipeHasRecipeTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recipeHasRecipeTags", allowSetters = true)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recipeHasRecipeTags", allowSetters = true)
    private RecipeTag recipeTag;

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

    public RecipeHasRecipeTag status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeHasRecipeTag recipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeTag getRecipeTag() {
        return recipeTag;
    }

    public RecipeHasRecipeTag recipeTag(RecipeTag recipeTag) {
        this.recipeTag = recipeTag;
        return this;
    }

    public void setRecipeTag(RecipeTag recipeTag) {
        this.recipeTag = recipeTag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeHasRecipeTag)) {
            return false;
        }
        return id != null && id.equals(((RecipeHasRecipeTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeHasRecipeTag{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
