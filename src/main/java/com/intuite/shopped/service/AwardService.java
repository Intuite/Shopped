package com.intuite.shopped.service;

import com.intuite.shopped.domain.Award;
import com.intuite.shopped.repository.AwardRepository;
import com.intuite.shopped.service.dto.AwardDTO;
import com.intuite.shopped.service.mapper.AwardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Award}.
 */
@Service
@Transactional
public class AwardService {

    private final Logger log = LoggerFactory.getLogger(AwardService.class);

    private final AwardRepository awardRepository;

    private final AwardMapper awardMapper;

    public AwardService(AwardRepository awardRepository, AwardMapper awardMapper) {
        this.awardRepository = awardRepository;
        this.awardMapper = awardMapper;
    }

    /**
     * Save a award.
     *
     * @param awardDTO the entity to save.
     * @return the persisted entity.
     */
    public AwardDTO save(AwardDTO awardDTO) {
        log.debug("Request to save Award : {}", awardDTO);
        Award award = awardMapper.toEntity(awardDTO);
        award = awardRepository.save(award);
        return awardMapper.toDto(award);
    }

    /**
     * Get all the awards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AwardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Awards");
        return awardRepository.findAll(pageable)
            .map(awardMapper::toDto);
    }


    /**
     * Get one award by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AwardDTO> findOne(Long id) {
        log.debug("Request to get Award : {}", id);
        return awardRepository.findById(id)
            .map(awardMapper::toDto);
    }

    /**
     * Delete the award by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Award : {}", id);
        awardRepository.deleteById(id);
    }
}
