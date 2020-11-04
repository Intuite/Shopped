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

import com.intuite.shopped.domain.CartHasRecipe;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.CartHasRecipeRepository;
import com.intuite.shopped.service.dto.CartHasRecipeCriteria;
import com.intuite.shopped.service.dto.CartHasRecipeDTO;
import com.intuite.shopped.service.mapper.CartHasRecipeMapper;

/**
 * Service for executing complex queries for {@link CartHasRecipe} entities in the database.
 * The main input is a {@link CartHasRecipeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CartHasRecipeDTO} or a {@link Page} of {@link CartHasRecipeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CartHasRecipeQueryService extends QueryService<CartHasRecipe> {

    private final Logger log = LoggerFactory.getLogger(CartHasRecipeQueryService.class);

    private final CartHasRecipeRepository cartHasRecipeRepository;

    private final CartHasRecipeMapper cartHasRecipeMapper;

    public CartHasRecipeQueryService(CartHasRecipeRepository cartHasRecipeRepository, CartHasRecipeMapper cartHasRecipeMapper) {
        this.cartHasRecipeRepository = cartHasRecipeRepository;
        this.cartHasRecipeMapper = cartHasRecipeMapper;
    }

    /**
     * Return a {@link List} of {@link CartHasRecipeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CartHasRecipeDTO> findByCriteria(CartHasRecipeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CartHasRecipe> specification = createSpecification(criteria);
        return cartHasRecipeMapper.toDto(cartHasRecipeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CartHasRecipeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CartHasRecipeDTO> findByCriteria(CartHasRecipeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CartHasRecipe> specification = createSpecification(criteria);
        return cartHasRecipeRepository.findAll(specification, page)
            .map(cartHasRecipeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CartHasRecipeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CartHasRecipe> specification = createSpecification(criteria);
        return cartHasRecipeRepository.count(specification);
    }

    /**
     * Function to convert {@link CartHasRecipeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CartHasRecipe> createSpecification(CartHasRecipeCriteria criteria) {
        Specification<CartHasRecipe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CartHasRecipe_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CartHasRecipe_.status));
            }
            if (criteria.getRecipeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeId(),
                    root -> root.join(CartHasRecipe_.recipe, JoinType.LEFT).get(Recipe_.id)));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartId(),
                    root -> root.join(CartHasRecipe_.cart, JoinType.LEFT).get(Cart_.id)));
            }
        }
        return specification;
    }
}
