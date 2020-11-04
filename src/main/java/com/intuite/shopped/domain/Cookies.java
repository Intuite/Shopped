package com.intuite.shopped.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * For the user money.\n@author Isaac Miranda
 */
@Entity
@Table(name = "cookies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cookies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cookies quantity.\n@version 1
     */
    @Min(value = 0)
    @Column(name = "amount")
    private Integer amount;

    /**
     * Cookies wallet key for\nPaypal.\n@version 1
     */
    @Column(name = "wallet_key")
    private String walletKey;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Cookies amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public Cookies walletKey(String walletKey) {
        this.walletKey = walletKey;
        return this;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public User getUser() {
        return user;
    }

    public Cookies user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cookies)) {
            return false;
        }
        return id != null && id.equals(((Cookies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cookies{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", walletKey='" + getWalletKey() + "'" +
            "}";
    }
}
