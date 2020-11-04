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

import com.intuite.shopped.domain.ReportComment;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.ReportCommentRepository;
import com.intuite.shopped.service.dto.ReportCommentCriteria;
import com.intuite.shopped.service.dto.ReportCommentDTO;
import com.intuite.shopped.service.mapper.ReportCommentMapper;

/**
 * Service for executing complex queries for {@link ReportComment} entities in the database.
 * The main input is a {@link ReportCommentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportCommentDTO} or a {@link Page} of {@link ReportCommentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportCommentQueryService extends QueryService<ReportComment> {

    private final Logger log = LoggerFactory.getLogger(ReportCommentQueryService.class);

    private final ReportCommentRepository reportCommentRepository;

    private final ReportCommentMapper reportCommentMapper;

    public ReportCommentQueryService(ReportCommentRepository reportCommentRepository, ReportCommentMapper reportCommentMapper) {
        this.reportCommentRepository = reportCommentRepository;
        this.reportCommentMapper = reportCommentMapper;
    }

    /**
     * Return a {@link List} of {@link ReportCommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportCommentDTO> findByCriteria(ReportCommentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportComment> specification = createSpecification(criteria);
        return reportCommentMapper.toDto(reportCommentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportCommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportCommentDTO> findByCriteria(ReportCommentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportComment> specification = createSpecification(criteria);
        return reportCommentRepository.findAll(specification, page)
            .map(reportCommentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportCommentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportComment> specification = createSpecification(criteria);
        return reportCommentRepository.count(specification);
    }

    /**
     * Function to convert {@link ReportCommentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReportComment> createSpecification(ReportCommentCriteria criteria) {
        Specification<ReportComment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReportComment_.id));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), ReportComment_.created));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ReportComment_.status));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(ReportComment_.type, JoinType.LEFT).get(ReportType_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(ReportComment_.comment, JoinType.LEFT).get(Comment_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ReportComment_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
