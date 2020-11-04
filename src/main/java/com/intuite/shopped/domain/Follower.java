package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.intuite.shopped.domain.enumeration.Status;

/**
 * When a user follow\nanother user.\n@author Isaac Miranda
 */
@Entity
@Table(name = "follower")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Follower implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "followers", allowSetters = true)
    private User userFollowed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "followers", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public Follower created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public Follower status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUserFollowed() {
        return userFollowed;
    }

    public Follower userFollowed(User user) {
        this.userFollowed = user;
        return this;
    }

    public void setUserFollowed(User user) {
        this.userFollowed = user;
    }

    public User getUser() {
        return user;
    }

    public Follower user(User user) {
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
        if (!(o instanceof Follower)) {
            return false;
        }
        return id != null && id.equals(((Follower) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Follower{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
