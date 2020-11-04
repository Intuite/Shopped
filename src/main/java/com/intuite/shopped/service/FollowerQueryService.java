package com.intuite.shopped.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.intuite.shopped.domain.Follower;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.FollowerRepository;
import com.intuite.shopped.service.dto.FollowerCriteria;
import com.intuite.shopped.service.dto.FollowerDTO;
import com.intuite.shopped.service.mapper.FollowerMapper;

/**
 * Service for executing complex queries for {@link Follower} entities in the database.
 * The main input is a {@link FollowerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FollowerDTO} or a {@link Page} of {@link FollowerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FollowerQueryService extends QueryService<Follower> {

    private final Logger log = LoggerFactory.getLogger(FollowerQueryService.class);

    private final FollowerRepository followerRepository;

    private final FollowerMapper followerMapper;

    public FollowerQueryService(FollowerRepository followerRepository, FollowerMapper followerMapper) {
        this.followerRepository = followerRepository;
        this.followerMapper = followerMapper;
    }

    /**
     * Return a {@link List} of {@link FollowerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FollowerDTO> findByCriteria(FollowerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Follower> specification = createSpecification(criteria);
        return followerMapper.toDto(followerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FollowerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FollowerDTO> findByCriteria(FollowerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Follower> specification = createSpecification(criteria);
        return followerRepository.findAll(specification, page)
            .map(followerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FollowerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Follower> specification = createSpecification(criteria);
        return followerRepository.count(specification);
    }

    /**
     * Function to convert {@link FollowerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Follower> createSpecification(FollowerCriteria criteria) {
        Specification<Follower> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Follower_.id));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Follower_.created));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Follower_.status));
            }
            if (criteria.getUserFollowedId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserFollowedId(),
                    root -> root.join(Follower_.userFollowed, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Follower_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
