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

import com.intuite.shopped.domain.RecipeShared;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.RecipeSharedRepository;
import com.intuite.shopped.service.dto.RecipeSharedCriteria;
import com.intuite.shopped.service.dto.RecipeSharedDTO;
import com.intuite.shopped.service.mapper.RecipeSharedMapper;

/**
 * Service for executing complex queries for {@link RecipeShared} entities in the database.
 * The main input is a {@link RecipeSharedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecipeSharedDTO} or a {@link Page} of {@link RecipeSharedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecipeSharedQueryService extends QueryService<RecipeShared> {

    private final Logger log = LoggerFactory.getLogger(RecipeSharedQueryService.class);

    private final RecipeSharedRepository recipeSharedRepository;

    private final RecipeSharedMapper recipeSharedMapper;

    public RecipeSharedQueryService(RecipeSharedRepository recipeSharedRepository, RecipeSharedMapper recipeSharedMapper) {
        this.recipeSharedRepository = recipeSharedRepository;
        this.recipeSharedMapper = recipeSharedMapper;
    }

    /**
     * Return a {@link List} of {@link RecipeSharedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeSharedDTO> findByCriteria(RecipeSharedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecipeShared> specification = createSpecification(criteria);
        return recipeSharedMapper.toDto(recipeSharedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecipeSharedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeSharedDTO> findByCriteria(RecipeSharedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecipeShared> specification = createSpecification(criteria);
        return recipeSharedRepository.findAll(specification, page)
            .map(recipeSharedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecipeSharedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecipeShared> specification = createSpecification(criteria);
        return recipeSharedRepository.count(specification);
    }

    /**
     * Function to convert {@link RecipeSharedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecipeShared> createSpecification(RecipeSharedCriteria criteria) {
        Specification<RecipeShared> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecipeShared_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), RecipeShared_.status));
            }
            if (criteria.getRecipeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeId(),
                    root -> root.join(RecipeShared_.recipe, JoinType.LEFT).get(Recipe_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(RecipeShared_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
