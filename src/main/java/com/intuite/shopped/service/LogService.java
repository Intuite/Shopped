package com.intuite.shopped.service;

import com.intuite.shopped.domain.Log;
import com.intuite.shopped.repository.LogRepository;
import com.intuite.shopped.service.dto.LogDTO;
import com.intuite.shopped.service.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Log}.
 */
@Service
@Transactional
public class LogService {

    private final Logger log = LoggerFactory.getLogger(LogService.class);

    private final LogRepository logRepository;

    private final LogMapper logMapper;

    public LogService(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    /**
     * Save a log.
     *
     * @param logDTO the entity to save.
     * @return the persisted entity.
     */
    public LogDTO save(LogDTO logDTO) {
        log.debug("Request to save Log : {}", logDTO);
        Log log = logMapper.toEntity(logDTO);
        log = logRepository.save(log);
        return logMapper.toDto(log);
    }

    /**
     * Get all the logs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Logs");
        return logRepository.findAll(pageable)
            .map(logMapper::toDto);
    }


    /**
     * Get one log by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogDTO> findOne(Long id) {
        log.debug("Request to get Log : {}", id);
        return logRepository.findById(id)
            .map(logMapper::toDto);
    }

    /**
     * Delete the log by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Log : {}", id);
        logRepository.deleteById(id);
    }
}
