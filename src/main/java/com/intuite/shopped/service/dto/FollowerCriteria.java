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
 * Criteria class for the {@link com.intuite.shopped.domain.Follower} entity. This class is used
 * in {@link com.intuite.shopped.web.rest.FollowerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /followers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FollowerCriteria implements Serializable, Criteria {
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

    private LongFilter userFollowedId;

    private LongFilter userId;

    public FollowerCriteria() {
    }

    public FollowerCriteria(FollowerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.userFollowedId = other.userFollowedId == null ? null : other.userFollowedId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public FollowerCriteria copy() {
        return new FollowerCriteria(this);
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

    public LongFilter getUserFollowedId() {
        return userFollowedId;
    }

    public void setUserFollowedId(LongFilter userFollowedId) {
        this.userFollowedId = userFollowedId;
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
        final FollowerCriteria that = (FollowerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(created, that.created) &&
            Objects.equals(status, that.status) &&
            Objects.equals(userFollowedId, that.userFollowedId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        created,
        status,
        userFollowedId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FollowerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (userFollowedId != null ? "userFollowedId=" + userFollowedId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
