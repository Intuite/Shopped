package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Log} entity.
 */
@ApiModel(description = "A log for every\nuser.\n@author Isaac Miranda")
public class LogDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    private Instant created;


    private Long typeId;

    private String typeName;

    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long logTypeId) {
        this.typeId = logTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String logTypeName) {
        this.typeName = logTypeName;
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
        if (!(o instanceof LogDTO)) {
            return false;
        }

        return id != null && id.equals(((LogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", created='" + getCreated() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
