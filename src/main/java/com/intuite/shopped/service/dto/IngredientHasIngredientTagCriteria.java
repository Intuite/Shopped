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
 * Criteria class for the {@link com.intuite.shopped.domain.IngredientHasIngredientTag} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.IngredientHasIngredientTagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ingredient-has-ingredient-tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IngredientHasIngredientTagCriteria implements Serializable, Criteria {
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

    private StatusFilter status;

    private LongFilter ingredientId;

    private LongFilter ingredientTagId;

    public IngredientHasIngredientTagCriteria() {
    }

    public IngredientHasIngredientTagCriteria(IngredientHasIngredientTagCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.ingredientId = other.ingredientId == null ? null : other.ingredientId.copy();
        this.ingredientTagId = other.ingredientTagId == null ? null : other.ingredientTagId.copy();
    }

    @Override
    public IngredientHasIngredientTagCriteria copy() {
        return new IngredientHasIngredientTagCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getIngredientTagId() {
        return ingredientTagId;
    }

    public void setIngredientTagId(LongFilter ingredientTagId) {
        this.ingredientTagId = ingredientTagId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IngredientHasIngredientTagCriteria that = (IngredientHasIngredientTagCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(ingredientId, that.ingredientId) &&
            Objects.equals(ingredientTagId, that.ingredientTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        ingredientId,
        ingredientTagId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientHasIngredientTagCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (ingredientId != null ? "ingredientId=" + ingredientId + ", " : "") +
                (ingredientTagId != null ? "ingredientTagId=" + ingredientTagId + ", " : "") +
            "}";
    }

}
