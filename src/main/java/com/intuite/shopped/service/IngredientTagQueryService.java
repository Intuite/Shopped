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

import com.intuite.shopped.domain.IngredientTag;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.IngredientTagRepository;
import com.intuite.shopped.service.dto.IngredientTagCriteria;
import com.intuite.shopped.service.dto.IngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientTagMapper;

/**
 * Service for executing complex queries for {@link IngredientTag} entities in the database.
 * The main input is a {@link IngredientTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IngredientTagDTO} or a {@link Page} of {@link IngredientTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IngredientTagQueryService extends QueryService<IngredientTag> {

    private final Logger log = LoggerFactory.getLogger(IngredientTagQueryService.class);

    private final IngredientTagRepository ingredientTagRepository;

    private final IngredientTagMapper ingredientTagMapper;

    public IngredientTagQueryService(IngredientTagRepository ingredientTagRepository, IngredientTagMapper ingredientTagMapper) {
        this.ingredientTagRepository = ingredientTagRepository;
        this.ingredientTagMapper = ingredientTagMapper;
    }

    /**
     * Return a {@link List} of {@link IngredientTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientTagDTO> findByCriteria(IngredientTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IngredientTag> specification = createSpecification(criteria);
        return ingredientTagMapper.toDto(ingredientTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IngredientTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredientTagDTO> findByCriteria(IngredientTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IngredientTag> specification = createSpecification(criteria);
        return ingredientTagRepository.findAll(specification, page)
            .map(ingredientTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IngredientTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IngredientTag> specification = createSpecification(criteria);
        return ingredientTagRepository.count(specification);
    }

    /**
     * Function to convert {@link IngredientTagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IngredientTag> createSpecification(IngredientTagCriteria criteria) {
        Specification<IngredientTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IngredientTag_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), IngredientTag_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), IngredientTag_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), IngredientTag_.status));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(IngredientTag_.type, JoinType.LEFT).get(TagType_.id)));
            }
        }
        return specification;
    }
}
