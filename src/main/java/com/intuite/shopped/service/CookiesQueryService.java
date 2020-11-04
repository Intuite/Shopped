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

import com.intuite.shopped.domain.Cookies;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.CookiesRepository;
import com.intuite.shopped.service.dto.CookiesCriteria;
import com.intuite.shopped.service.dto.CookiesDTO;
import com.intuite.shopped.service.mapper.CookiesMapper;

/**
 * Service for executing complex queries for {@link Cookies} entities in the database.
 * The main input is a {@link CookiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CookiesDTO} or a {@link Page} of {@link CookiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CookiesQueryService extends QueryService<Cookies> {

    private final Logger log = LoggerFactory.getLogger(CookiesQueryService.class);

    private final CookiesRepository cookiesRepository;

    private final CookiesMapper cookiesMapper;

    public CookiesQueryService(CookiesRepository cookiesRepository, CookiesMapper cookiesMapper) {
        this.cookiesRepository = cookiesRepository;
        this.cookiesMapper = cookiesMapper;
    }

    /**
     * Return a {@link List} of {@link CookiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CookiesDTO> findByCriteria(CookiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cookies> specification = createSpecification(criteria);
        return cookiesMapper.toDto(cookiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CookiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CookiesDTO> findByCriteria(CookiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cookies> specification = createSpecification(criteria);
        return cookiesRepository.findAll(specification, page)
            .map(cookiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CookiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cookies> specification = createSpecification(criteria);
        return cookiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CookiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cookies> createSpecification(CookiesCriteria criteria) {
        Specification<Cookies> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cookies_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Cookies_.amount));
            }
            if (criteria.getWalletKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWalletKey(), Cookies_.walletKey));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Cookies_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
