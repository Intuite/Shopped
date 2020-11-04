package com.intuite.shopped.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.intuite.shopped.domain.enumeration.Status;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.intuite.shopped.domain.RecipeHasIngredient} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.RecipeHasIngredientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recipe-has-ingredients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecipeHasIngredientCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {
        }

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter amount;

    private StatusFilter status;

    private LongFilter ingredientId;

    private LongFilter recipeId;

    public RecipeHasIngredientCriteria() {
    }

    public RecipeHasIngredientCriteria(RecipeHasIngredientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.ingredientId = other.ingredientId == null ? null : other.ingredientId.copy();
        this.recipeId = other.recipeId == null ? null : other.recipeId.copy();
    }

    @Override
    public RecipeHasIngredientCriteria copy() {
        return new RecipeHasIngredientCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getAmount() {
        return amount;
    }

    public void setAmount(IntegerFilter amount) {
        this.amount = amount;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public LongFilter getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(LongFilter ingredientId) {
        this.ingredientId = ingredientId;
    }

    public LongFilter getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(LongFilter recipeId) {
        this.recipeId = recipeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RecipeHasIngredientCriteria that = (RecipeHasIngredientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(ingredientId, that.ingredientId) &&
            Objects.equals(recipeId, that.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        status,
        ingredientId,
        recipeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeHasIngredientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (ingredientId != null ? "ingredientId=" + ingredientId + ", " : "") +
                (recipeId != null ? "recipeId=" + recipeId + ", " : "") +
            "}";
    }

}
