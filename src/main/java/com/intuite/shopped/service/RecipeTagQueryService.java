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

import com.intuite.shopped.domain.RecipeTag;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.RecipeTagRepository;
import com.intuite.shopped.service.dto.RecipeTagCriteria;
import com.intuite.shopped.service.dto.RecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeTagMapper;

/**
 * Service for executing complex queries for {@link RecipeTag} entities in the database.
 * The main input is a {@link RecipeTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RecipeTagDTO} or a {@link Page} of {@link RecipeTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecipeTagQueryService extends QueryService<RecipeTag> {

    private final Logger log = LoggerFactory.getLogger(RecipeTagQueryService.class);

    private final RecipeTagRepository recipeTagRepository;

    private final RecipeTagMapper recipeTagMapper;

    public RecipeTagQueryService(RecipeTagRepository recipeTagRepository, RecipeTagMapper recipeTagMapper) {
        this.recipeTagRepository = recipeTagRepository;
        this.recipeTagMapper = recipeTagMapper;
    }

    /**
     * Return a {@link List} of {@link RecipeTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RecipeTagDTO> findByCriteria(RecipeTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RecipeTag> specification = createSpecification(criteria);
        return recipeTagMapper.toDto(recipeTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RecipeTagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeTagDTO> findByCriteria(RecipeTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RecipeTag> specification = createSpecification(criteria);
        return recipeTagRepository.findAll(specification, page)
            .map(recipeTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecipeTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RecipeTag> specification = createSpecification(criteria);
        return recipeTagRepository.count(specification);
    }

    /**
     * Function to convert {@link RecipeTagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RecipeTag> createSpecification(RecipeTagCriteria criteria) {
        Specification<RecipeTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RecipeTag_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RecipeTag_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RecipeTag_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), RecipeTag_.status));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(RecipeTag_.type, JoinType.LEFT).get(TagType_.id)));
            }
        }
        return specification;
    }
}
