package com.intuite.shopped.service;

import com.intuite.shopped.domain.IngredientTag;
import com.intuite.shopped.repository.IngredientTagRepository;
import com.intuite.shopped.service.dto.IngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IngredientTag}.
 */
@Service
@Transactional
public class IngredientTagService {

    private final Logger log = LoggerFactory.getLogger(IngredientTagService.class);

    private final IngredientTagRepository ingredientTagRepository;

    private final IngredientTagMapper ingredientTagMapper;

    public IngredientTagService(IngredientTagRepository ingredientTagRepository, IngredientTagMapper ingredientTagMapper) {
        this.ingredientTagRepository = ingredientTagRepository;
        this.ingredientTagMapper = ingredientTagMapper;
    }

    /**
     * Save a ingredientTag.
     *
     * @param ingredientTagDTO the entity to save.
     * @return the persisted entity.
     */
    public IngredientTagDTO save(IngredientTagDTO ingredientTagDTO) {
        log.debug("Request to save IngredientTag : {}", ingredientTagDTO);
        IngredientTag ingredientTag = ingredientTagMapper.toEntity(ingredientTagDTO);
        ingredientTag = ingredientTagRepository.save(ingredientTag);
        return ingredientTagMapper.toDto(ingredientTag);
    }

    /**
     * Get all the ingredientTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IngredientTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientTags");
        return ingredientTagRepository.findAll(pageable)
            .map(ingredientTagMapper::toDto);
    }


    /**
     * Get one ingredientTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IngredientTagDTO> findOne(Long id) {
        log.debug("Request to get IngredientTag : {}", id);
        return ingredientTagRepository.findById(id)
            .map(ingredientTagMapper::toDto);
    }

    /**
     * Delete the ingredientTag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IngredientTag : {}", id);
        ingredientTagRepository.deleteById(id);
    }
}
