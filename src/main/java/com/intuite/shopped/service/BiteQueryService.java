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

import com.intuite.shopped.domain.Bite;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.BiteRepository;
import com.intuite.shopped.service.dto.BiteCriteria;
import com.intuite.shopped.service.dto.BiteDTO;
import com.intuite.shopped.service.mapper.BiteMapper;

/**
 * Service for executing complex queries for {@link Bite} entities in the database.
 * The main input is a {@link BiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BiteDTO} or a {@link Page} of {@link BiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BiteQueryService extends QueryService<Bite> {

    private final Logger log = LoggerFactory.getLogger(BiteQueryService.class);

    private final BiteRepository biteRepository;

    private final BiteMapper biteMapper;

    public BiteQueryService(BiteRepository biteRepository, BiteMapper biteMapper) {
        this.biteRepository = biteRepository;
        this.biteMapper = biteMapper;
    }

    /**
     * Return a {@link List} of {@link BiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BiteDTO> findByCriteria(BiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bite> specification = createSpecification(criteria);
        return biteMapper.toDto(biteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BiteDTO> findByCriteria(BiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bite> specification = createSpecification(criteria);
        return biteRepository.findAll(specification, page)
            .map(biteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bite> specification = createSpecification(criteria);
        return biteRepository.count(specification);
    }

    /**
     * Function to convert {@link BiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bite> createSpecification(BiteCriteria criteria) {
        Specification<Bite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bite_.id));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Bite_.created));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Bite_.status));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Bite_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Bite_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
