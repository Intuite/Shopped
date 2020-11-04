package com.intuite.shopped.service;

import com.intuite.shopped.domain.Bite;
import com.intuite.shopped.repository.BiteRepository;
import com.intuite.shopped.service.dto.BiteDTO;
import com.intuite.shopped.service.mapper.BiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bite}.
 */
@Service
@Transactional
public class BiteService {

    private final Logger log = LoggerFactory.getLogger(BiteService.class);

    private final BiteRepository biteRepository;

    private final BiteMapper biteMapper;

    public BiteService(BiteRepository biteRepository, BiteMapper biteMapper) {
        this.biteRepository = biteRepository;
        this.biteMapper = biteMapper;
    }

    /**
     * Save a bite.
     *
     * @param biteDTO the entity to save.
     * @return the persisted entity.
     */
    public BiteDTO save(BiteDTO biteDTO) {
        log.debug("Request to save Bite : {}", biteDTO);
        Bite bite = biteMapper.toEntity(biteDTO);
        bite = biteRepository.save(bite);
        return biteMapper.toDto(bite);
    }

    /**
     * Get all the bites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bites");
        return biteRepository.findAll(pageable)
            .map(biteMapper::toDto);
    }


    /**
     * Get one bite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BiteDTO> findOne(Long id) {
        log.debug("Request to get Bite : {}", id);
        return biteRepository.findById(id)
            .map(biteMapper::toDto);
    }

    /**
     * Delete the bite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bite : {}", id);
        biteRepository.deleteById(id);
    }
}
