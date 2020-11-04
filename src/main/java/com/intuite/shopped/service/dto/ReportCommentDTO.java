package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.ReportComment} entity.
 */
@ApiModel(description = "For reporting\ncomments.\n@author Isaac Miranda")
public class ReportCommentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant created;

    private Status status;


    private Long typeId;

    private String typeName;

    private Long commentId;

    private String commentContent;

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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long reportTypeId) {
        this.typeId = reportTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String reportTypeName) {
        this.typeName = reportTypeName;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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
        if (!(o instanceof ReportCommentDTO)) {
            return false;
        }

        return id != null && id.equals(((ReportCommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportCommentDTO{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            ", typeId=" + getTypeId() +
            ", typeName='" + getTypeName() + "'" +
            ", commentId=" + getCommentId() +
            ", commentContent='" + getCommentContent() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
