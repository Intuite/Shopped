package com.intuite.shopped.service;

import com.intuite.shopped.domain.LogType;
import com.intuite.shopped.repository.LogTypeRepository;
import com.intuite.shopped.service.dto.LogTypeDTO;
import com.intuite.shopped.service.mapper.LogTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LogType}.
 */
@Service
@Transactional
public class LogTypeService {

    private final Logger log = LoggerFactory.getLogger(LogTypeService.class);

    private final LogTypeRepository logTypeRepository;

    private final LogTypeMapper logTypeMapper;

    public LogTypeService(LogTypeRepository logTypeRepository, LogTypeMapper logTypeMapper) {
        this.logTypeRepository = logTypeRepository;
        this.logTypeMapper = logTypeMapper;
    }

    /**
     * Save a logType.
     *
     * @param logTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public LogTypeDTO save(LogTypeDTO logTypeDTO) {
        log.debug("Request to save LogType : {}", logTypeDTO);
        LogType logType = logTypeMapper.toEntity(logTypeDTO);
        logType = logTypeRepository.save(logType);
        return logTypeMapper.toDto(logType);
    }

    /**
     * Get all the logTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogTypes");
        return logTypeRepository.findAll(pageable)
            .map(logTypeMapper::toDto);
    }


    /**
     * Get one logType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogTypeDTO> findOne(Long id) {
        log.debug("Request to get LogType : {}", id);
        return logTypeRepository.findById(id)
            .map(logTypeMapper::toDto);
    }

    /**
     * Delete the logType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LogType : {}", id);
        logTypeRepository.deleteById(id);
    }
}
