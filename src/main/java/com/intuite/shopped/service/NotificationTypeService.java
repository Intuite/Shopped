package com.intuite.shopped.service;

import com.intuite.shopped.domain.NotificationType;
import com.intuite.shopped.repository.NotificationTypeRepository;
import com.intuite.shopped.service.dto.NotificationTypeDTO;
import com.intuite.shopped.service.mapper.NotificationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificationType}.
 */
@Service
@Transactional
public class NotificationTypeService {

    private final Logger log = LoggerFactory.getLogger(NotificationTypeService.class);

    private final NotificationTypeRepository notificationTypeRepository;

    private final NotificationTypeMapper notificationTypeMapper;

    public NotificationTypeService(NotificationTypeRepository notificationTypeRepository, NotificationTypeMapper notificationTypeMapper) {
        this.notificationTypeRepository = notificationTypeRepository;
        this.notificationTypeMapper = notificationTypeMapper;
    }

    /**
     * Save a notificationType.
     *
     * @param notificationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationTypeDTO save(NotificationTypeDTO notificationTypeDTO) {
        log.debug("Request to save NotificationType : {}", notificationTypeDTO);
        NotificationType notificationType = notificationTypeMapper.toEntity(notificationTypeDTO);
        notificationType = notificationTypeRepository.save(notificationType);
        return notificationTypeMapper.toDto(notificationType);
    }

    /**
     * Get all the notificationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationTypes");
        return notificationTypeRepository.findAll(pageable)
            .map(notificationTypeMapper::toDto);
    }


    /**
     * Get one notificationType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationTypeDTO> findOne(Long id) {
        log.debug("Request to get NotificationType : {}", id);
        return notificationTypeRepository.findById(id)
            .map(notificationTypeMapper::toDto);
    }

    /**
     * Delete the notificationType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationType : {}", id);
        notificationTypeRepository.deleteById(id);
    }
}
