package com.intuite.shopped.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Intermediate table for\na user that wants to\ngive an a ward to a\npost.\n@author Isaac Miranda
 */
@Entity
@Table(name = "commendation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commendation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commendations", allowSetters = true)
    private Post post;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commendations", allowSetters = true)
    private Award award;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "commendations", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Commendation date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Post getPost() {
        return post;
    }

    public Commendation post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Award getAward() {
        return award;
    }

    public Commendation award(Award award) {
        this.award = award;
        return this;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public User getUser() {
        return user;
    }

    public Commendation user(User user) {
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
        if (!(o instanceof Commendation)) {
            return false;
        }
        return id != null && id.equals(((Commendation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commendation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
