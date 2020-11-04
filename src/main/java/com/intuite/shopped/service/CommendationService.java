package com.intuite.shopped.service;

import com.intuite.shopped.domain.Commendation;
import com.intuite.shopped.repository.CommendationRepository;
import com.intuite.shopped.service.dto.CommendationDTO;
import com.intuite.shopped.service.mapper.CommendationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commendation}.
 */
@Service
@Transactional
public class CommendationService {

    private final Logger log = LoggerFactory.getLogger(CommendationService.class);

    private final CommendationRepository commendationRepository;

    private final CommendationMapper commendationMapper;

    public CommendationService(CommendationRepository commendationRepository, CommendationMapper commendationMapper) {
        this.commendationRepository = commendationRepository;
        this.commendationMapper = commendationMapper;
    }

    /**
     * Save a commendation.
     *
     * @param commendationDTO the entity to save.
     * @return the persisted entity.
     */
    public CommendationDTO save(CommendationDTO commendationDTO) {
        log.debug("Request to save Commendation : {}", commendationDTO);
        Commendation commendation = commendationMapper.toEntity(commendationDTO);
        commendation = commendationRepository.save(commendation);
        return commendationMapper.toDto(commendation);
    }

    /**
     * Get all the commendations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommendationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commendations");
        return commendationRepository.findAll(pageable)
            .map(commendationMapper::toDto);
    }


    /**
     * Get one commendation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommendationDTO> findOne(Long id) {
        log.debug("Request to get Commendation : {}", id);
        return commendationRepository.findById(id)
            .map(commendationMapper::toDto);
    }

    /**
     * Delete the commendation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commendation : {}", id);
        commendationRepository.deleteById(id);
    }
}
