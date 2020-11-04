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

import com.intuite.shopped.domain.IngredientHasIngredientTag;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.IngredientHasIngredientTagRepository;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagCriteria;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientHasIngredientTagMapper;

/**
 * Service for executing complex queries for {@link IngredientHasIngredientTag} entities in the database.
 * The main input is a {@link IngredientHasIngredientTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IngredientHasIngredientTagDTO} or a {@link Page} of {@link IngredientHasIngredientTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IngredientHasIngredientTagQueryService extends QueryService<IngredientHasIngredientTag> {

    private final Logger log = LoggerFactory.getLogger(IngredientHasIngredientTagQueryService.class);

    private final IngredientHasIngredientTagRepository ingredientHasIngredientTagRepository;

    private final IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper;

    public IngredientHasIngredientTagQueryService(IngredientHasIngredientTagRepository ingredientHasIngredientTagRepository, IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper) {
        this.ingredientHasIngredientTagRepository = ingredientHasIngredientTagRepository;
        this.ingredientHasIngredientTagMapper = ingredientHasIngredientTagMapper;
    }

    /**
     * Return a {@link List} of {@link IngredientHasIngredientTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientHasIngredientTagDTO> findByCriteria(IngredientHasIngredientTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IngredientHasIngredientTag> specification = createSpecification(criteria);
        return ingredientHasIngredientTagMapper.toDto(ingredientHasIngredientTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IngredientHasIngredientTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredientHasIngredientTagDTO> findByCriteria(IngredientHasIngredientTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IngredientHasIngredientTag> specification = createSpecification(criteria);
        return ingredientHasIngredientTagRepository.findAll(specification, page)
            .map(ingredientHasIngredientTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IngredientHasIngredientTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IngredientHasIngredientTag> specification = createSpecification(criteria);
        return ingredientHasIngredientTagRepository.count(specification);
    }

    /**
     * Function to convert {@link IngredientHasIngredientTagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IngredientHasIngredientTag> createSpecification(IngredientHasIngredientTagCriteria criteria) {
        Specification<IngredientHasIngredientTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IngredientHasIngredientTag_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), IngredientHasIngredientTag_.status));
            }
            if (criteria.getIngredientId() != null) {
                specification = specification.and(buildSpecification(criteria.getIngredientId(),
                    root -> root.join(IngredientHasIngredientTag_.ingredient, JoinType.LEFT).get(Ingredient_.id)));
            }
            if (criteria.getIngredientTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getIngredientTagId(),
                    root -> root.join(IngredientHasIngredientTag_.ingredientTag, JoinType.LEFT).get(IngredientTag_.id)));
            }
        }
        return specification;
    }
}
