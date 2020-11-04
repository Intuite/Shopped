package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Follower} entity.
 */
@ApiModel(description = "When a user follow\nanother user.\n@author Isaac Miranda")
public class FollowerDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant created;

    private Status status;


    private Long userFollowedId;

    private String userFollowedLogin;

    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserFollowedId() {
        return userFollowedId;
    }

    public void setUserFollowedId(Long userId) {
        this.userFollowedId = userId;
    }

    public String getUserFollowedLogin() {
        return userFollowedLogin;
    }

    public void setUserFollowedLogin(String userLogin) {
        this.userFollowedLogin = userLogin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FollowerDTO)) {
            return false;
        }

        return id != null && id.equals(((FollowerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FollowerDTO{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            ", userFollowedId=" + getUserFollowedId() +
            ", userFollowedLogin='" + getUserFollowedLogin() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
