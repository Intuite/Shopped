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
 * Criteria class for the {@link com.intuite.shopped.domain.RecipeHasRecipeTag} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.RecipeHasRecipeTagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recipe-has-recipe-tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecipeHasRecipeTagCriteria implements Serializable, Criteria {
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

    private LongFilter recipeId;

    private LongFilter recipeTagId;

    public RecipeHasRecipeTagCriteria() {
    }

    public RecipeHasRecipeTagCriteria(RecipeHasRecipeTagCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.recipeId = other.recipeId == null ? null : other.recipeId.copy();
        this.recipeTagId = other.recipeTagId == null ? null : other.recipeTagId.copy();
    }

    @Override
    public RecipeHasRecipeTagCriteria copy() {
        return new RecipeHasRecipeTagCriteria(this);
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

    public LongFilter getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(LongFilter recipeId) {
        this.recipeId = recipeId;
    }

    public LongFilter getRecipeTagId() {
        return recipeTagId;
    }

    public void setRecipeTagId(LongFilter recipeTagId) {
        this.recipeTagId = recipeTagId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RecipeHasRecipeTagCriteria that = (RecipeHasRecipeTagCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(recipeId, that.recipeId) &&
            Objects.equals(recipeTagId, that.recipeTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        recipeId,
        recipeTagId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeHasRecipeTagCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (recipeId != null ? "recipeId=" + recipeId + ", " : "") +
                (recipeTagId != null ? "recipeTagId=" + recipeTagId + ", " : "") +
            "}";
    }

}
