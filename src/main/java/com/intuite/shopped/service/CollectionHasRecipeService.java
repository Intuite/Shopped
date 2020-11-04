package com.intuite.shopped.service;

import com.intuite.shopped.domain.CollectionHasRecipe;
import com.intuite.shopped.repository.CollectionHasRecipeRepository;
import com.intuite.shopped.service.dto.CollectionHasRecipeDTO;
import com.intuite.shopped.service.mapper.CollectionHasRecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CollectionHasRecipe}.
 */
@Service
@Transactional
public class CollectionHasRecipeService {

    private final Logger log = LoggerFactory.getLogger(CollectionHasRecipeService.class);

    private final CollectionHasRecipeRepository collectionHasRecipeRepository;

    private final CollectionHasRecipeMapper collectionHasRecipeMapper;

    public CollectionHasRecipeService(CollectionHasRecipeRepository collectionHasRecipeRepository, CollectionHasRecipeMapper collectionHasRecipeMapper) {
        this.collectionHasRecipeRepository = collectionHasRecipeRepository;
        this.collectionHasRecipeMapper = collectionHasRecipeMapper;
    }

    /**
     * Save a collectionHasRecipe.
     *
     * @param collectionHasRecipeDTO the entity to save.
     * @return the persisted entity.
     */
    public CollectionHasRecipeDTO save(CollectionHasRecipeDTO collectionHasRecipeDTO) {
        log.debug("Request to save CollectionHasRecipe : {}", collectionHasRecipeDTO);
        CollectionHasRecipe collectionHasRecipe = collectionHasRecipeMapper.toEntity(collectionHasRecipeDTO);
        collectionHasRecipe = collectionHasRecipeRepository.save(collectionHasRecipe);
        return collectionHasRecipeMapper.toDto(collectionHasRecipe);
    }

    /**
     * Get all the collectionHasRecipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CollectionHasRecipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CollectionHasRecipes");
        return collectionHasRecipeRepository.findAll(pageable)
            .map(collectionHasRecipeMapper::toDto);
    }


    /**
     * Get one collectionHasRecipe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollectionHasRecipeDTO> findOne(Long id) {
        log.debug("Request to get CollectionHasRecipe : {}", id);
        return collectionHasRecipeRepository.findById(id)
            .map(collectionHasRecipeMapper::toDto);
    }

    /**
     * Delete the collectionHasRecipe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CollectionHasRecipe : {}", id);
        collectionHasRecipeRepository.deleteById(id);
    }
}
