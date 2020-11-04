package com.intuite.shopped.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.intuite.shopped.domain.Cookies} entity.
 */
@ApiModel(description = "For the user money.\n@author Isaac Miranda")
public class CookiesDTO implements Serializable {
    
    private Long id;

    /**
     * Cookies quantity.\n@version 1
     */
    @Min(value = 0)
    @ApiModelProperty(value = "Cookies quantity.\n@version 1")
    private Integer amount;

    /**
     * Cookies wallet key for\nPaypal.\n@version 1
     */
    @ApiModelProperty(value = "Cookies wallet key for\nPaypal.\n@version 1")
    private String walletKey;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
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
        if (!(o instanceof CookiesDTO)) {
            return false;
        }

        return id != null && id.equals(((CookiesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CookiesDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", walletKey='" + getWalletKey() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
