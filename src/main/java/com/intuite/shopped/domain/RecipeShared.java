package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.intuite.shopped.domain.enumeration.Status;

/**
 * Intermediate entity\nfor recipes and the\nusers that have\naccess.\n@author Isaac Miranda
 */
@Entity
@Table(name = "recipe_shared")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecipeShared implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recipeShareds", allowSetters = true)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "recipeShareds", allowSetters = true)
    private User user;

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

    public RecipeShared status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeShared recipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return user;
    }

    public RecipeShared user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeShared)) {
            return false;
        }
        return id != null && id.equals(((RecipeShared) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeShared{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
