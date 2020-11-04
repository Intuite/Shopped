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
 * For reporting\nposts.\n@author Isaac Miranda
 */
@Entity
@Table(name = "report_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReportPost implements Serializable {

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
    @JsonIgnoreProperties(value = "reportPosts", allowSetters = true)
    private ReportType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportPosts", allowSetters = true)
    private Post post;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "reportPosts", allowSetters = true)
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

    public ReportPost created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public ReportPost status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ReportType getType() {
        return type;
    }

    public ReportPost type(ReportType reportType) {
        this.type = reportType;
        return this;
    }

    public void setType(ReportType reportType) {
        this.type = reportType;
    }

    public Post getPost() {
        return post;
    }

    public ReportPost post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public ReportPost user(User user) {
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
        if (!(o instanceof ReportPost)) {
            return false;
        }
        return id != null && id.equals(((ReportPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportPost{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
