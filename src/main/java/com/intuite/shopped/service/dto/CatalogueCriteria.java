package com.intuite.shopped.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.intuite.shopped.domain.Catalogue} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.CatalogueResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /catalogues?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CatalogueCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idCatalogue;

    private StringFilter value;

    public CatalogueCriteria() {
    }

    public CatalogueCriteria(CatalogueCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idCatalogue = other.idCatalogue == null ? null : other.idCatalogue.copy();
        this.value = other.value == null ? null : other.value.copy();
    }

    @Override
    public CatalogueCriteria copy() {
        return new CatalogueCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdCatalogue() {
        return idCatalogue;
    }

    public void setIdCatalogue(StringFilter idCatalogue) {
        this.idCatalogue = idCatalogue;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CatalogueCriteria that = (CatalogueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idCatalogue, that.idCatalogue) &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idCatalogue,
        value
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idCatalogue != null ? "idCatalogue=" + idCatalogue + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
            "}";
    }

}
