package com.intuite.shopped.service;

import com.intuite.shopped.domain.RecipeHasIngredient;
import com.intuite.shopped.repository.RecipeHasIngredientRepository;
import com.intuite.shopped.service.dto.RecipeHasIngredientDTO;
import com.intuite.shopped.service.mapper.RecipeHasIngredientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RecipeHasIngredient}.
 */
@Service
@Transactional
public class RecipeHasIngredientService {

    private final Logger log = LoggerFactory.getLogger(RecipeHasIngredientService.class);

    private final RecipeHasIngredientRepository recipeHasIngredientRepository;

    private final RecipeHasIngredientMapper recipeHasIngredientMapper;

    public RecipeHasIngredientService(RecipeHasIngredientRepository recipeHasIngredientRepository, RecipeHasIngredientMapper recipeHasIngredientMapper) {
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.recipeHasIngredientMapper = recipeHasIngredientMapper;
    }

    /**
     * Save a recipeHasIngredient.
     *
     * @param recipeHasIngredientDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipeHasIngredientDTO save(RecipeHasIngredientDTO recipeHasIngredientDTO) {
        log.debug("Request to save RecipeHasIngredient : {}", recipeHasIngredientDTO);
        RecipeHasIngredient recipeHasIngredient = recipeHasIngredientMapper.toEntity(recipeHasIngredientDTO);
        recipeHasIngredient = recipeHasIngredientRepository.save(recipeHasIngredient);
        return recipeHasIngredientMapper.toDto(recipeHasIngredient);
    }

    /**
     * Get all the recipeHasIngredients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipeHasIngredientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeHasIngredients");
        return recipeHasIngredientRepository.findAll(pageable)
            .map(recipeHasIngredientMapper::toDto);
    }


    /**
     * Get one recipeHasIngredient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipeHasIngredientDTO> findOne(Long id) {
        log.debug("Request to get RecipeHasIngredient : {}", id);
        return recipeHasIngredientRepository.findById(id)
            .map(recipeHasIngredientMapper::toDto);
    }

    /**
     * Delete the recipeHasIngredient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeHasIngredient : {}", id);
        recipeHasIngredientRepository.deleteById(id);
    }
}
