package com.intuite.shopped.service;

import com.intuite.shopped.domain.IngredientHasIngredientTag;
import com.intuite.shopped.repository.IngredientHasIngredientTagRepository;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientHasIngredientTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IngredientHasIngredientTag}.
 */
@Service
@Transactional
public class IngredientHasIngredientTagService {

    private final Logger log = LoggerFactory.getLogger(IngredientHasIngredientTagService.class);

    private final IngredientHasIngredientTagRepository ingredientHasIngredientTagRepository;

    private final IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper;

    public IngredientHasIngredientTagService(IngredientHasIngredientTagRepository ingredientHasIngredientTagRepository, IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper) {
        this.ingredientHasIngredientTagRepository = ingredientHasIngredientTagRepository;
        this.ingredientHasIngredientTagMapper = ingredientHasIngredientTagMapper;
    }

    /**
     * Save a ingredientHasIngredientTag.
     *
     * @param ingredientHasIngredientTagDTO the entity to save.
     * @return the persisted entity.
     */
    public IngredientHasIngredientTagDTO save(IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO) {
        log.debug("Request to save IngredientHasIngredientTag : {}", ingredientHasIngredientTagDTO);
        IngredientHasIngredientTag ingredientHasIngredientTag = ingredientHasIngredientTagMapper.toEntity(ingredientHasIngredientTagDTO);
        ingredientHasIngredientTag = ingredientHasIngredientTagRepository.save(ingredientHasIngredientTag);
        return ingredientHasIngredientTagMapper.toDto(ingredientHasIngredientTag);
    }

    /**
     * Get all the ingredientHasIngredientTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredientHasIngredientTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientHasIngredientTags");
        return ingredientHasIngredientTagRepository.findAll(pageable)
            .map(ingredientHasIngredientTagMapper::toDto);
    }


    /**
     * Get one ingredientHasIngredientTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IngredientHasIngredientTagDTO> findOne(Long id) {
        log.debug("Request to get IngredientHasIngredientTag : {}", id);
        return ingredientHasIngredientTagRepository.findById(id)
            .map(ingredientHasIngredientTagMapper::toDto);
    }

    /**
     * Delete the ingredientHasIngredientTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IngredientHasIngredientTag : {}", id);
        ingredientHasIngredientTagRepository.deleteById(id);
    }
}
