package com.intuite.shopped.service;

import com.intuite.shopped.domain.RecipeTag;
import com.intuite.shopped.repository.RecipeTagRepository;
import com.intuite.shopped.service.dto.RecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecipeTag}.
 */
@Service
@Transactional
public class RecipeTagService {

    private final Logger log = LoggerFactory.getLogger(RecipeTagService.class);

    private final RecipeTagRepository recipeTagRepository;

    private final RecipeTagMapper recipeTagMapper;

    public RecipeTagService(RecipeTagRepository recipeTagRepository, RecipeTagMapper recipeTagMapper) {
        this.recipeTagRepository = recipeTagRepository;
        this.recipeTagMapper = recipeTagMapper;
    }

    /**
     * Save a recipeTag.
     *
     * @param recipeTagDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeTagDTO save(RecipeTagDTO recipeTagDTO) {
        log.debug("Request to save RecipeTag : {}", recipeTagDTO);
        RecipeTag recipeTag = recipeTagMapper.toEntity(recipeTagDTO);
        recipeTag = recipeTagRepository.save(recipeTag);
        return recipeTagMapper.toDto(recipeTag);
    }

    /**
     * Get all the recipeTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeTags");
        return recipeTagRepository.findAll(pageable)
            .map(recipeTagMapper::toDto);
    }


    /**
     * Get one recipeTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeTagDTO> findOne(Long id) {
        log.debug("Request to get RecipeTag : {}", id);
        return recipeTagRepository.findById(id)
            .map(recipeTagMapper::toDto);
    }

    /**
     * Delete the recipeTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeTag : {}", id);
        recipeTagRepository.deleteById(id);
    }
}
