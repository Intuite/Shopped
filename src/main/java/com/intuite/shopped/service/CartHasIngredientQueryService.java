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

import com.intuite.shopped.domain.CartHasIngredient;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.CartHasIngredientRepository;
import com.intuite.shopped.service.dto.CartHasIngredientCriteria;
import com.intuite.shopped.service.dto.CartHasIngredientDTO;
import com.intuite.shopped.service.mapper.CartHasIngredientMapper;

/**
 * Service for executing complex queries for {@link CartHasIngredient} entities in the database.
 * The main input is a {@link CartHasIngredientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CartHasIngredientDTO} or a {@link Page} of {@link CartHasIngredientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CartHasIngredientQueryService extends QueryService<CartHasIngredient> {

    private final Logger log = LoggerFactory.getLogger(CartHasIngredientQueryService.class);

    private final CartHasIngredientRepository cartHasIngredientRepository;

    private final CartHasIngredientMapper cartHasIngredientMapper;

    public CartHasIngredientQueryService(CartHasIngredientRepository cartHasIngredientRepository, CartHasIngredientMapper cartHasIngredientMapper) {
        this.cartHasIngredientRepository = cartHasIngredientRepository;
        this.cartHasIngredientMapper = cartHasIngredientMapper;
    }

    /**
     * Return a {@link List} of {@link CartHasIngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CartHasIngredientDTO> findByCriteria(CartHasIngredientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CartHasIngredient> specification = createSpecification(criteria);
        return cartHasIngredientMapper.toDto(cartHasIngredientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CartHasIngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CartHasIngredientDTO> findByCriteria(CartHasIngredientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CartHasIngredient> specification = createSpecification(criteria);
        return cartHasIngredientRepository.findAll(specification, page)
            .map(cartHasIngredientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CartHasIngredientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CartHasIngredient> specification = createSpecification(criteria);
        return cartHasIngredientRepository.count(specification);
    }

    /**
     * Function to convert {@link CartHasIngredientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CartHasIngredient> createSpecification(CartHasIngredientCriteria criteria) {
        Specification<CartHasIngredient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CartHasIngredient_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CartHasIngredient_.amount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CartHasIngredient_.status));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartId(),
                    root -> root.join(CartHasIngredient_.cart, JoinType.LEFT).get(Cart_.id)));
            }
            if (criteria.getIngredientId() != null) {
                specification = specification.and(buildSpecification(criteria.getIngredientId(),
                    root -> root.join(CartHasIngredient_.ingredient, JoinType.LEFT).get(Ingredient_.id)));
            }
        }
        return specification;
    }
}
