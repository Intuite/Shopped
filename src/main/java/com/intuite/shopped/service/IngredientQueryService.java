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

import com.intuite.shopped.domain.Ingredient;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.IngredientRepository;
import com.intuite.shopped.service.dto.IngredientCriteria;
import com.intuite.shopped.service.dto.IngredientDTO;
import com.intuite.shopped.service.mapper.IngredientMapper;

/**
 * Service for executing complex queries for {@link Ingredient} entities in the database.
 * The main input is a {@link IngredientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IngredientDTO} or a {@link Page} of {@link IngredientDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IngredientQueryService extends QueryService<Ingredient> {

    private final Logger log = LoggerFactory.getLogger(IngredientQueryService.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    public IngredientQueryService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Return a {@link List} of {@link IngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientDTO> findByCriteria(IngredientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ingredient> specification = createSpecification(criteria);
        return ingredientMapper.toDto(ingredientRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IngredientDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredientDTO> findByCriteria(IngredientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ingredient> specification = createSpecification(criteria);
        return ingredientRepository.findAll(specification, page)
            .map(ingredientMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IngredientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ingredient> specification = createSpecification(criteria);
        return ingredientRepository.count(specification);
    }

    /**
     * Function to convert {@link IngredientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ingredient> createSpecification(IngredientCriteria criteria) {
        Specification<Ingredient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ingredient_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Ingredient_.name));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Ingredient_.status));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Ingredient_.description));
            }
            if (criteria.getUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitId(),
                    root -> root.join(Ingredient_.unit, JoinType.LEFT).get(Unit_.id)));
            }
        }
        return specification;
    }
}
