package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Transaction} entity.
 */
@ApiModel(description = "Payment for Cookies\nwith Paypal.\n@author Isaac Miranda")
public class TransactionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Float amount;

    @NotNull
    private Instant created;

    @NotNull
    private String description;

    @NotNull
    private Integer cookiesAmount;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCookiesAmount() {
        return cookiesAmount;
    }

    public void setCookiesAmount(Integer cookiesAmount) {
        this.cookiesAmount = cookiesAmount;
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
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", created='" + getCreated() + "'" +
            ", description='" + getDescription() + "'" +
            ", cookiesAmount=" + getCookiesAmount() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
