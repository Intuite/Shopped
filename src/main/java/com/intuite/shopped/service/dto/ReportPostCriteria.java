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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.intuite.shopped.domain.ReportPost} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.ReportPostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /report-posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReportPostCriteria implements Serializable, Criteria {
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

    private InstantFilter created;

    private StatusFilter status;

    private LongFilter typeId;

    private LongFilter postId;

    private LongFilter userId;

    public ReportPostCriteria() {
    }

    public ReportPostCriteria(ReportPostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public ReportPostCriteria copy() {
        return new ReportPostCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreated() {
        return created;
    }

    public void setCreated(InstantFilter created) {
        this.created = created;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReportPostCriteria that = (ReportPostCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(created, that.created) &&
            Objects.equals(status, that.status) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        created,
        status,
        typeId,
        postId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportPostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
