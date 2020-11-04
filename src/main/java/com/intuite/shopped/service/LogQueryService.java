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

import com.intuite.shopped.domain.Log;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.LogRepository;
import com.intuite.shopped.service.dto.LogCriteria;
import com.intuite.shopped.service.dto.LogDTO;
import com.intuite.shopped.service.mapper.LogMapper;

/**
 * Service for executing complex queries for {@link Log} entities in the database.
 * The main input is a {@link LogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogDTO} or a {@link Page} of {@link LogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogQueryService extends QueryService<Log> {

    private final Logger log = LoggerFactory.getLogger(LogQueryService.class);

    private final LogRepository logRepository;

    private final LogMapper logMapper;

    public LogQueryService(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    /**
     * Return a {@link List} of {@link LogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogDTO> findByCriteria(LogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Log> specification = createSpecification(criteria);
        return logMapper.toDto(logRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogDTO> findByCriteria(LogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Log> specification = createSpecification(criteria);
        return logRepository.findAll(specification, page)
            .map(logMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Log> specification = createSpecification(criteria);
        return logRepository.count(specification);
    }

    /**
     * Function to convert {@link LogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Log> createSpecification(LogCriteria criteria) {
        Specification<Log> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Log_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Log_.description));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Log_.created));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(Log_.type, JoinType.LEFT).get(LogType_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Log_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
