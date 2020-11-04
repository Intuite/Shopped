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

import com.intuite.shopped.domain.NotificationType;
import com.intuite.shopped.domain.*; // for static metamodels
import com.intuite.shopped.repository.NotificationTypeRepository;
import com.intuite.shopped.service.dto.NotificationTypeCriteria;
import com.intuite.shopped.service.dto.NotificationTypeDTO;
import com.intuite.shopped.service.mapper.NotificationTypeMapper;

/**
 * Service for executing complex queries for {@link NotificationType} entities in the database.
 * The main input is a {@link NotificationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationTypeDTO} or a {@link Page} of {@link NotificationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationTypeQueryService extends QueryService<NotificationType> {

    private final Logger log = LoggerFactory.getLogger(NotificationTypeQueryService.class);

    private final NotificationTypeRepository notificationTypeRepository;

    private final NotificationTypeMapper notificationTypeMapper;

    public NotificationTypeQueryService(NotificationTypeRepository notificationTypeRepository, NotificationTypeMapper notificationTypeMapper) {
        this.notificationTypeRepository = notificationTypeRepository;
        this.notificationTypeMapper = notificationTypeMapper;
    }

    /**
     * Return a {@link List} of {@link NotificationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationTypeDTO> findByCriteria(NotificationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationType> specification = createSpecification(criteria);
        return notificationTypeMapper.toDto(notificationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationTypeDTO> findByCriteria(NotificationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationType> specification = createSpecification(criteria);
        return notificationTypeRepository.findAll(specification, page)
            .map(notificationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationType> specification = createSpecification(criteria);
        return notificationTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationType> createSpecification(NotificationTypeCriteria criteria) {
        Specification<NotificationType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NotificationType_.name));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), NotificationType_.status));
            }
        }
        return specification;
    }
}
