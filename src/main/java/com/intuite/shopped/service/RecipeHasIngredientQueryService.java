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

import com.intuite.shopped.domain.RecipeHasIngredient;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.RecipeHasIngredientRepository;
import com.intuite.shopped.service.dto.RecipeHasIngredientCriteria;
import com.intuite.shopped.service.dto.RecipeHasIngredientDTO;
import com.intuite.shopped.service.mapper.RecipeHasIngredientMapper;

/**
 * Service for executing complex queries for {@link RecipeHasIngredient} entities in the database.
 * The main input is a {@link RecipeHasIngredientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecipeHasIngredientDTO} or a {@link Page} of {@link RecipeHasIngredientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecipeHasIngredientQueryService extends QueryService<RecipeHasIngredient> {

    private final Logger log = LoggerFactory.getLogger(RecipeHasIngredientQueryService.class);

    private final RecipeHasIngredientRepository recipeHasIngredientRepository;

    private final RecipeHasIngredientMapper recipeHasIngredientMapper;

    public RecipeHasIngredientQueryService(RecipeHasIngredientRepository recipeHasIngredientRepository, RecipeHasIngredientMapper recipeHasIngredientMapper) {
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.recipeHasIngredientMapper = recipeHasIngredientMapper;
    }

    /**
     * Return a {@link List} of {@link RecipeHasIngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeHasIngredientDTO> findByCriteria(RecipeHasIngredientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecipeHasIngredient> specification = createSpecification(criteria);
        return recipeHasIngredientMapper.toDto(recipeHasIngredientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecipeHasIngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeHasIngredientDTO> findByCriteria(RecipeHasIngredientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecipeHasIngredient> specification = createSpecification(criteria);
        return recipeHasIngredientRepository.findAll(specification, page)
            .map(recipeHasIngredientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecipeHasIngredientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecipeHasIngredient> specification = createSpecification(criteria);
        return recipeHasIngredientRepository.count(specification);
    }

    /**
     * Function to convert {@link RecipeHasIngredientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecipeHasIngredient> createSpecification(RecipeHasIngredientCriteria criteria) {
        Specification<RecipeHasIngredient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecipeHasIngredient_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), RecipeHasIngredient_.amount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), RecipeHasIngredient_.status));
            }
            if (criteria.getIngredientId() != null) {
                specification = specification.and(buildSpecification(criteria.getIngredientId(),
                    root -> root.join(RecipeHasIngredient_.ingredient, JoinType.LEFT).get(Ingredient_.id)));
            }
            if (criteria.getRecipeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeId(),
                    root -> root.join(RecipeHasIngredient_.recipe, JoinType.LEFT).get(Recipe_.id)));
            }
        }
        return specification;
    }
}
