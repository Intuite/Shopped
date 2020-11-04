package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.intuite.shopped.domain.enumeration.Status;

/**
 * A DTO for the {@link com.intuite.shopped.domain.LogType} entity.
 */
@ApiModel(description = "A type for any log\n@author Isaac Miranda")
public class LogTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String template;

    private Status status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((LogTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", template='" + getTemplate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
