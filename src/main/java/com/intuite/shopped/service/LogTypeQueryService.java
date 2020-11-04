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

import com.intuite.shopped.domain.LogType;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.LogTypeRepository;
import com.intuite.shopped.service.dto.LogTypeCriteria;
import com.intuite.shopped.service.dto.LogTypeDTO;
import com.intuite.shopped.service.mapper.LogTypeMapper;

/**
 * Service for executing complex queries for {@link LogType} entities in the database.
 * The main input is a {@link LogTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogTypeDTO} or a {@link Page} of {@link LogTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogTypeQueryService extends QueryService<LogType> {

    private final Logger log = LoggerFactory.getLogger(LogTypeQueryService.class);

    private final LogTypeRepository logTypeRepository;

    private final LogTypeMapper logTypeMapper;

    public LogTypeQueryService(LogTypeRepository logTypeRepository, LogTypeMapper logTypeMapper) {
        this.logTypeRepository = logTypeRepository;
        this.logTypeMapper = logTypeMapper;
    }

    /**
     * Return a {@link List} of {@link LogTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogTypeDTO> findByCriteria(LogTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LogType> specification = createSpecification(criteria);
        return logTypeMapper.toDto(logTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LogTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogTypeDTO> findByCriteria(LogTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LogType> specification = createSpecification(criteria);
        return logTypeRepository.findAll(specification, page)
            .map(logTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LogType> specification = createSpecification(criteria);
        return logTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link LogTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LogType> createSpecification(LogTypeCriteria criteria) {
        Specification<LogType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LogType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LogType_.name));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemplate(), LogType_.template));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LogType_.status));
            }
        }
        return specification;
    }
}
