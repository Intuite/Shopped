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

import com.intuite.shopped.domain.TagType;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.TagTypeRepository;
import com.intuite.shopped.service.dto.TagTypeCriteria;
import com.intuite.shopped.service.dto.TagTypeDTO;
import com.intuite.shopped.service.mapper.TagTypeMapper;

/**
 * Service for executing complex queries for {@link TagType} entities in the database.
 * The main input is a {@link TagTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TagTypeDTO} or a {@link Page} of {@link TagTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TagTypeQueryService extends QueryService<TagType> {

    private final Logger log = LoggerFactory.getLogger(TagTypeQueryService.class);

    private final TagTypeRepository tagTypeRepository;

    private final TagTypeMapper tagTypeMapper;

    public TagTypeQueryService(TagTypeRepository tagTypeRepository, TagTypeMapper tagTypeMapper) {
        this.tagTypeRepository = tagTypeRepository;
        this.tagTypeMapper = tagTypeMapper;
    }

    /**
     * Return a {@link List} of {@link TagTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TagTypeDTO> findByCriteria(TagTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TagType> specification = createSpecification(criteria);
        return tagTypeMapper.toDto(tagTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TagTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TagTypeDTO> findByCriteria(TagTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TagType> specification = createSpecification(criteria);
        return tagTypeRepository.findAll(specification, page)
            .map(tagTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TagTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TagType> specification = createSpecification(criteria);
        return tagTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link TagTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TagType> createSpecification(TagTypeCriteria criteria) {
        Specification<TagType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TagType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TagType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TagType_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), TagType_.status));
            }
        }
        return specification;
    }
}
