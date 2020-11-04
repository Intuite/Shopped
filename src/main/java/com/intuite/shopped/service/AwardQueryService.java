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

import com.intuite.shopped.domain.Award;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.AwardRepository;
import com.intuite.shopped.service.dto.AwardCriteria;
import com.intuite.shopped.service.dto.AwardDTO;
import com.intuite.shopped.service.mapper.AwardMapper;

/**
 * Service for executing complex queries for {@link Award} entities in the database.
 * The main input is a {@link AwardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AwardDTO} or a {@link Page} of {@link AwardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AwardQueryService extends QueryService<Award> {

    private final Logger log = LoggerFactory.getLogger(AwardQueryService.class);

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    public AwardQueryService(AwardRepository awardRepository, AwardMapper awardMapper) {
        this.awardRepository = awardRepository;
        this.awardMapper = awardMapper;
    }

    /**
     * Return a {@link List} of {@link AwardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AwardDTO> findByCriteria(AwardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Award> specification = createSpecification(criteria);
        return awardMapper.toDto(awardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AwardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AwardDTO> findByCriteria(AwardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Award> specification = createSpecification(criteria);
        return awardRepository.findAll(specification, page)
            .map(awardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AwardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Award> specification = createSpecification(criteria);
        return awardRepository.count(specification);
    }

    /**
     * Function to convert {@link AwardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Award> createSpecification(AwardCriteria criteria) {
        Specification<Award> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Award_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Award_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Award_.description));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Award_.cost));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Award_.status));
            }
        }
        return specification;
    }
}
