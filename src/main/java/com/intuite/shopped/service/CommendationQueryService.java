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

import com.intuite.shopped.domain.Commendation;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.CommendationRepository;
import com.intuite.shopped.service.dto.CommendationCriteria;
import com.intuite.shopped.service.dto.CommendationDTO;
import com.intuite.shopped.service.mapper.CommendationMapper;

/**
 * Service for executing complex queries for {@link Commendation} entities in the database.
 * The main input is a {@link CommendationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommendationDTO} or a {@link Page} of {@link CommendationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommendationQueryService extends QueryService<Commendation> {

    private final Logger log = LoggerFactory.getLogger(CommendationQueryService.class);

    private final CommendationRepository commendationRepository;

    private final CommendationMapper commendationMapper;

    public CommendationQueryService(CommendationRepository commendationRepository, CommendationMapper commendationMapper) {
        this.commendationRepository = commendationRepository;
        this.commendationMapper = commendationMapper;
    }

    /**
     * Return a {@link List} of {@link CommendationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommendationDTO> findByCriteria(CommendationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commendation> specification = createSpecification(criteria);
        return commendationMapper.toDto(commendationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommendationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommendationDTO> findByCriteria(CommendationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commendation> specification = createSpecification(criteria);
        return commendationRepository.findAll(specification, page)
            .map(commendationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommendationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commendation> specification = createSpecification(criteria);
        return commendationRepository.count(specification);
    }

    /**
     * Function to convert {@link CommendationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commendation> createSpecification(CommendationCriteria criteria) {
        Specification<Commendation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commendation_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Commendation_.date));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Commendation_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getAwardId() != null) {
                specification = specification.and(buildSpecification(criteria.getAwardId(),
                    root -> root.join(Commendation_.award, JoinType.LEFT).get(Award_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Commendation_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
