package com.intuite.shopped.service;

import com.intuite.shopped.domain.RecipeShared;
import com.intuite.shopped.repository.RecipeSharedRepository;
import com.intuite.shopped.service.dto.RecipeSharedDTO;
import com.intuite.shopped.service.mapper.RecipeSharedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecipeShared}.
 */
@Service
@Transactional
public class RecipeSharedService {

    private final Logger log = LoggerFactory.getLogger(RecipeSharedService.class);

    private final RecipeSharedRepository recipeSharedRepository;

    private final RecipeSharedMapper recipeSharedMapper;

    public RecipeSharedService(RecipeSharedRepository recipeSharedRepository, RecipeSharedMapper recipeSharedMapper) {
        this.recipeSharedRepository = recipeSharedRepository;
        this.recipeSharedMapper = recipeSharedMapper;
    }

    /**
     * Save a recipeShared.
     *
     * @param recipeSharedDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeSharedDTO save(RecipeSharedDTO recipeSharedDTO) {
        log.debug("Request to save RecipeShared : {}", recipeSharedDTO);
        RecipeShared recipeShared = recipeSharedMapper.toEntity(recipeSharedDTO);
        recipeShared = recipeSharedRepository.save(recipeShared);
        return recipeSharedMapper.toDto(recipeShared);
    }

    /**
     * Get all the recipeShareds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeSharedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeShareds");
        return recipeSharedRepository.findAll(pageable)
            .map(recipeSharedMapper::toDto);
    }


    /**
     * Get one recipeShared by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeSharedDTO> findOne(Long id) {
        log.debug("Request to get RecipeShared : {}", id);
        return recipeSharedRepository.findById(id)
            .map(recipeSharedMapper::toDto);
    }

    /**
     * Delete the recipeShared by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeShared : {}", id);
        recipeSharedRepository.deleteById(id);
    }
}
