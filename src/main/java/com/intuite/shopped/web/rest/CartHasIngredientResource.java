package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.CartHasIngredientService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.CartHasIngredientDTO;
import com.intuite.shopped.service.dto.CartHasIngredientCriteria;
import com.intuite.shopped.service.CartHasIngredientQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.intuite.shopped.domain.CartHasIngredient}.
 */
@RestController
@RequestMapping("/api")
public class CartHasIngredientResource {

    private final Logger log = LoggerFactory.getLogger(CartHasIngredientResource.class);

    private static final String ENTITY_NAME = "cartHasIngredient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartHasIngredientService cartHasIngredientService;

    private final CartHasIngredientQueryService cartHasIngredientQueryService;

    public CartHasIngredientResource(CartHasIngredientService cartHasIngredientService, CartHasIngredientQueryService cartHasIngredientQueryService) {
        this.cartHasIngredientService = cartHasIngredientService;
        this.cartHasIngredientQueryService = cartHasIngredientQueryService;
    }

    /**
     * {@code POST  /cart-has-ingredients} : Create a new cartHasIngredient.
     *
     * @param cartHasIngredientDTO the cartHasIngredientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartHasIngredientDTO, or with status {@code 400 (Bad Request)} if the cartHasIngredient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-has-ingredients")
    public ResponseEntity<CartHasIngredientDTO> createCartHasIngredient(@Valid @RequestBody CartHasIngredientDTO cartHasIngredientDTO) throws URISyntaxException {
        log.debug("REST request to save CartHasIngredient : {}", cartHasIngredientDTO);
        if (cartHasIngredientDTO.getId() != null) {
            throw new BadRequestAlertException("A new cartHasIngredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartHasIngredientDTO result = cartHasIngredientService.save(cartHasIngredientDTO);
        return ResponseEntity.created(new URI("/api/cart-has-ingredients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cart-has-ingredients} : Updates an existing cartHasIngredient.
     *
     * @param cartHasIngredientDTO the cartHasIngredientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartHasIngredientDTO,
     * or with status {@code 400 (Bad Request)} if the cartHasIngredientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartHasIngredientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-has-ingredients")
    public ResponseEntity<CartHasIngredientDTO> updateCartHasIngredient(@Valid @RequestBody CartHasIngredientDTO cartHasIngredientDTO) throws URISyntaxException {
        log.debug("REST request to update CartHasIngredient : {}", cartHasIngredientDTO);
        if (cartHasIngredientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartHasIngredientDTO result = cartHasIngredientService.save(cartHasIngredientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartHasIngredientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cart-has-ingredients} : get all the cartHasIngredients.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartHasIngredients in body.
     */
    @GetMapping("/cart-has-ingredients")
    public ResponseEntity<List<CartHasIngredientDTO>> getAllCartHasIngredients(CartHasIngredientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CartHasIngredients by criteria: {}", criteria);
        Page<CartHasIngredientDTO> page = cartHasIngredientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cart-has-ingredients/count} : count all the cartHasIngredients.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cart-has-ingredients/count")
    public ResponseEntity<Long> countCartHasIngredients(CartHasIngredientCriteria criteria) {
        log.debug("REST request to count CartHasIngredients by criteria: {}", criteria);
        return ResponseEntity.ok().body(cartHasIngredientQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cart-has-ingredients/:id} : get the "id" cartHasIngredient.
     *
     * @param id the id of the cartHasIngredientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartHasIngredientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-has-ingredients/{id}")
    public ResponseEntity<CartHasIngredientDTO> getCartHasIngredient(@PathVariable Long id) {
        log.debug("REST request to get CartHasIngredient : {}", id);
        Optional<CartHasIngredientDTO> cartHasIngredientDTO = cartHasIngredientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartHasIngredientDTO);
    }

    /**
     * {@code DELETE  /cart-has-ingredients/:id} : delete the "id" cartHasIngredient.
     *
     * @param id the id of the cartHasIngredientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-has-ingredients/{id}")
    public ResponseEntity<Void> deleteCartHasIngredient(@PathVariable Long id) {
        log.debug("REST request to delete CartHasIngredient : {}", id);
        cartHasIngredientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
