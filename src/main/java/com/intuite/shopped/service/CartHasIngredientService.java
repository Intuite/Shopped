package com.intuite.shopped.service;

import com.intuite.shopped.domain.CartHasIngredient;
import com.intuite.shopped.repository.CartHasIngredientRepository;
import com.intuite.shopped.service.dto.CartHasIngredientDTO;
import com.intuite.shopped.service.mapper.CartHasIngredientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CartHasIngredient}.
 */
@Service
@Transactional
public class CartHasIngredientService {

    private final Logger log = LoggerFactory.getLogger(CartHasIngredientService.class);

    private final CartHasIngredientRepository cartHasIngredientRepository;

    private final CartHasIngredientMapper cartHasIngredientMapper;

    public CartHasIngredientService(CartHasIngredientRepository cartHasIngredientRepository, CartHasIngredientMapper cartHasIngredientMapper) {
        this.cartHasIngredientRepository = cartHasIngredientRepository;
        this.cartHasIngredientMapper = cartHasIngredientMapper;
    }

    /**
     * Save a cartHasIngredient.
     *
     * @param cartHasIngredientDTO the entity to save.
     * @return the persisted entity.
     */
    public CartHasIngredientDTO save(CartHasIngredientDTO cartHasIngredientDTO) {
        log.debug("Request to save CartHasIngredient : {}", cartHasIngredientDTO);
        CartHasIngredient cartHasIngredient = cartHasIngredientMapper.toEntity(cartHasIngredientDTO);
        cartHasIngredient = cartHasIngredientRepository.save(cartHasIngredient);
        return cartHasIngredientMapper.toDto(cartHasIngredient);
    }

    /**
     * Get all the cartHasIngredients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CartHasIngredientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartHasIngredients");
        return cartHasIngredientRepository.findAll(pageable)
            .map(cartHasIngredientMapper::toDto);
    }


    /**
     * Get one cartHasIngredient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CartHasIngredientDTO> findOne(Long id) {
        log.debug("Request to get CartHasIngredient : {}", id);
        return cartHasIngredientRepository.findById(id)
            .map(cartHasIngredientMapper::toDto);
    }

    /**
     * Delete the cartHasIngredient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CartHasIngredient : {}", id);
        cartHasIngredientRepository.deleteById(id);
    }
}
