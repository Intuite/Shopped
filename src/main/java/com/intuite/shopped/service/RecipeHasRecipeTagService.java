package com.intuite.shopped.service;

import com.intuite.shopped.domain.RecipeHasRecipeTag;
import com.intuite.shopped.repository.RecipeHasRecipeTagRepository;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeHasRecipeTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecipeHasRecipeTag}.
 */
@Service
@Transactional
public class RecipeHasRecipeTagService {

    private final Logger log = LoggerFactory.getLogger(RecipeHasRecipeTagService.class);

    private final RecipeHasRecipeTagRepository recipeHasRecipeTagRepository;

    private final RecipeHasRecipeTagMapper recipeHasRecipeTagMapper;

    public RecipeHasRecipeTagService(RecipeHasRecipeTagRepository recipeHasRecipeTagRepository, RecipeHasRecipeTagMapper recipeHasRecipeTagMapper) {
        this.recipeHasRecipeTagRepository = recipeHasRecipeTagRepository;
        this.recipeHasRecipeTagMapper = recipeHasRecipeTagMapper;
    }

    /**
     * Save a recipeHasRecipeTag.
     *
     * @param recipeHasRecipeTagDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeHasRecipeTagDTO save(RecipeHasRecipeTagDTO recipeHasRecipeTagDTO) {
        log.debug("Request to save RecipeHasRecipeTag : {}", recipeHasRecipeTagDTO);
        RecipeHasRecipeTag recipeHasRecipeTag = recipeHasRecipeTagMapper.toEntity(recipeHasRecipeTagDTO);
        recipeHasRecipeTag = recipeHasRecipeTagRepository.save(recipeHasRecipeTag);
        return recipeHasRecipeTagMapper.toDto(recipeHasRecipeTag);
    }

    /**
     * Get all the recipeHasRecipeTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeHasRecipeTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeHasRecipeTags");
        return recipeHasRecipeTagRepository.findAll(pageable)
            .map(recipeHasRecipeTagMapper::toDto);
    }


    /**
     * Get one recipeHasRecipeTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeHasRecipeTagDTO> findOne(Long id) {
        log.debug("Request to get RecipeHasRecipeTag : {}", id);
        return recipeHasRecipeTagRepository.findById(id)
            .map(recipeHasRecipeTagMapper::toDto);
    }

    /**
     * Delete the recipeHasRecipeTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeHasRecipeTag : {}", id);
        recipeHasRecipeTagRepository.deleteById(id);
    }
}
