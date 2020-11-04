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

import com.intuite.shopped.domain.CollectionHasRecipe;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.CollectionHasRecipeRepository;
import com.intuite.shopped.service.dto.CollectionHasRecipeCriteria;
import com.intuite.shopped.service.dto.CollectionHasRecipeDTO;
import com.intuite.shopped.service.mapper.CollectionHasRecipeMapper;

/**
 * Service for executing complex queries for {@link CollectionHasRecipe} entities in the database.
 * The main input is a {@link CollectionHasRecipeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CollectionHasRecipeDTO} or a {@link Page} of {@link CollectionHasRecipeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CollectionHasRecipeQueryService extends QueryService<CollectionHasRecipe> {

    private final Logger log = LoggerFactory.getLogger(CollectionHasRecipeQueryService.class);

    private final CollectionHasRecipeRepository collectionHasRecipeRepository;

    private final CollectionHasRecipeMapper collectionHasRecipeMapper;

    public CollectionHasRecipeQueryService(CollectionHasRecipeRepository collectionHasRecipeRepository, CollectionHasRecipeMapper collectionHasRecipeMapper) {
        this.collectionHasRecipeRepository = collectionHasRecipeRepository;
        this.collectionHasRecipeMapper = collectionHasRecipeMapper;
    }

    /**
     * Return a {@link List} of {@link CollectionHasRecipeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CollectionHasRecipeDTO> findByCriteria(CollectionHasRecipeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CollectionHasRecipe> specification = createSpecification(criteria);
        return collectionHasRecipeMapper.toDto(collectionHasRecipeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CollectionHasRecipeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CollectionHasRecipeDTO> findByCriteria(CollectionHasRecipeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CollectionHasRecipe> specification = createSpecification(criteria);
        return collectionHasRecipeRepository.findAll(specification, page)
            .map(collectionHasRecipeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CollectionHasRecipeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CollectionHasRecipe> specification = createSpecification(criteria);
        return collectionHasRecipeRepository.count(specification);
    }

    /**
     * Function to convert {@link CollectionHasRecipeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CollectionHasRecipe> createSpecification(CollectionHasRecipeCriteria criteria) {
        Specification<CollectionHasRecipe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CollectionHasRecipe_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CollectionHasRecipe_.status));
            }
            if (criteria.getCollectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCollectionId(),
                    root -> root.join(CollectionHasRecipe_.collection, JoinType.LEFT).get(Collection_.id)));
            }
            if (criteria.getRecipeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecipeId(),
                    root -> root.join(CollectionHasRecipe_.recipe, JoinType.LEFT).get(Recipe_.id)));
            }
        }
        return specification;
    }
}
