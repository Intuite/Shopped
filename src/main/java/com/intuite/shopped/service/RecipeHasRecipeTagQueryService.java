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

import com.intuite.shopped.domain.RecipeHasRecipeTag;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.RecipeHasRecipeTagRepository;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagCriteria;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeHasRecipeTagMapper;

/**
 * Service for executing complex queries for {@link RecipeHasRecipeTag} entities in the database.
 * The main input is a {@link RecipeHasRecipeTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecipeHasRecipeTagDTO} or a {@link Page} of {@link RecipeHasRecipeTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecipeHasRecipeTagQueryService extends QueryService<RecipeHasRecipeTag> {

    private final Logger log = LoggerFactory.getLogger(RecipeHasRecipeTagQueryService.class);

    private final RecipeHasRecipeTagRepository recipeHasRecipeTagRepository;

    private final RecipeHasRecipeTagMapper recipeHasRecipeTagMapper;

    public RecipeHasRecipeTagQueryService(RecipeHasRecipeTagRepository recipeHasRecipeTagRepository, RecipeHasRecipeTagMapper recipeHasRecipeTagMapper) {
        this.recipeHasRecipeTagRepository = recipeHasRecipeTagRepository;
        this.recipeHasRecipeTagMapper = recipeHasRecipeTagMapper;
    }

    /**
     * Return a {@link List} of {@link RecipeHasRecipeTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeHasRecipeTagDTO> findByCriteria(RecipeHasRecipeTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecipeHasRecipeTag> specification = createSpecification(criteria);
        return recipeHasRecipeTagMapper.toDto(recipeHasRecipeTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecipeHasRecipeTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeHasRecipeTagDTO> findByCriteria(RecipeHasRecipeTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecipeHasRecipeTag> specification = createSpecification(criteria);
        return recipeHasRecipeTagRepository.findAll(specification, page)
            .map(recipeHasRecipeTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecipeHasRecipeTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecipeHasRecipeTag> specification = createSpecification(criteria);
        return recipeHasRecipeTagRepository.count(specification);
    }

    /**
     * Function to convert {@link RecipeHasRecipeTagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecipeHasRecipeTag> createSpecification(RecipeHasRecipeTagCriteria criteria) {
        Specification<RecipeHasRecipeTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecipeHasRecipeTag_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), RecipeHasRecipeTag_.status));
            }
            if (criteria.getRecipeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeId(),
                    root -> root.join(RecipeHasRecipeTag_.recipe, JoinType.LEFT).get(Recipe_.id)));
            }
            if (criteria.getRecipeTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeTagId(),
                    root -> root.join(RecipeHasRecipeTag_.recipeTag, JoinType.LEFT).get(RecipeTag_.id)));
            }
        }
        return specification;
    }
}
