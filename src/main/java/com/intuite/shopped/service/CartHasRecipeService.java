package com.intuite.shopped.service;

import com.intuite.shopped.domain.CartHasRecipe;
import com.intuite.shopped.repository.CartHasRecipeRepository;
import com.intuite.shopped.service.dto.CartHasRecipeDTO;
import com.intuite.shopped.service.mapper.CartHasRecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CartHasRecipe}.
 */
@Service
@Transactional
public class CartHasRecipeService {

    private final Logger log = LoggerFactory.getLogger(CartHasRecipeService.class);

    private final CartHasRecipeRepository cartHasRecipeRepository;

    private final CartHasRecipeMapper cartHasRecipeMapper;

    public CartHasRecipeService(CartHasRecipeRepository cartHasRecipeRepository, CartHasRecipeMapper cartHasRecipeMapper) {
        this.cartHasRecipeRepository = cartHasRecipeRepository;
        this.cartHasRecipeMapper = cartHasRecipeMapper;
    }

    /**
     * Save a cartHasRecipe.
     *
     * @param cartHasRecipeDTO the entity to save.
     * @return the persisted entity.
     */
    public CartHasRecipeDTO save(CartHasRecipeDTO cartHasRecipeDTO) {
        log.debug("Request to save CartHasRecipe : {}", cartHasRecipeDTO);
        CartHasRecipe cartHasRecipe = cartHasRecipeMapper.toEntity(cartHasRecipeDTO);
        cartHasRecipe = cartHasRecipeRepository.save(cartHasRecipe);
        return cartHasRecipeMapper.toDto(cartHasRecipe);
    }

    /**
     * Get all the cartHasRecipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CartHasRecipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartHasRecipes");
        return cartHasRecipeRepository.findAll(pageable)
            .map(cartHasRecipeMapper::toDto);
    }


    /**
     * Get one cartHasRecipe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CartHasRecipeDTO> findOne(Long id) {
        log.debug("Request to get CartHasRecipe : {}", id);
        return cartHasRecipeRepository.findById(id)
            .map(cartHasRecipeMapper::toDto);
    }

    /**
     * Delete the cartHasRecipe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CartHasRecipe : {}", id);
        cartHasRecipeRepository.deleteById(id);
    }
}
