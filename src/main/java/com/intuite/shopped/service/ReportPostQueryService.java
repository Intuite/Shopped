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

import com.intuite.shopped.domain.ReportPost;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.ReportPostRepository;
import com.intuite.shopped.service.dto.ReportPostCriteria;
import com.intuite.shopped.service.dto.ReportPostDTO;
import com.intuite.shopped.service.mapper.ReportPostMapper;

/**
 * Service for executing complex queries for {@link ReportPost} entities in the database.
 * The main input is a {@link ReportPostCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportPostDTO} or a {@link Page} of {@link ReportPostDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportPostQueryService extends QueryService<ReportPost> {

    private final Logger log = LoggerFactory.getLogger(ReportPostQueryService.class);

    private final ReportPostRepository reportPostRepository;

    private final ReportPostMapper reportPostMapper;

    public ReportPostQueryService(ReportPostRepository reportPostRepository, ReportPostMapper reportPostMapper) {
        this.reportPostRepository = reportPostRepository;
        this.reportPostMapper = reportPostMapper;
    }

    /**
     * Return a {@link List} of {@link ReportPostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportPostDTO> findByCriteria(ReportPostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportPost> specification = createSpecification(criteria);
        return reportPostMapper.toDto(reportPostRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportPostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportPostDTO> findByCriteria(ReportPostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportPost> specification = createSpecification(criteria);
        return reportPostRepository.findAll(specification, page)
            .map(reportPostMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportPostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportPost> specification = createSpecification(criteria);
        return reportPostRepository.count(specification);
    }

    /**
     * Function to convert {@link ReportPostCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReportPost> createSpecification(ReportPostCriteria criteria) {
        Specification<ReportPost> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReportPost_.id));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), ReportPost_.created));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ReportPost_.status));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(ReportPost_.type, JoinType.LEFT).get(ReportType_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(ReportPost_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ReportPost_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
