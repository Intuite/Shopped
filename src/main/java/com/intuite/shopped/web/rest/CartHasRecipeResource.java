package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.CartHasRecipeService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.CartHasRecipeDTO;
import com.intuite.shopped.service.dto.CartHasRecipeCriteria;
import com.intuite.shopped.service.CartHasRecipeQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.CartHasRecipe}.
 */
@RestController
@RequestMapping("/api")
public class CartHasRecipeResource {

    private final Logger log = LoggerFactory.getLogger(CartHasRecipeResource.class);

    private static final String ENTITY_NAME = "cartHasRecipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartHasRecipeService cartHasRecipeService;

    private final CartHasRecipeQueryService cartHasRecipeQueryService;

    public CartHasRecipeResource(CartHasRecipeService cartHasRecipeService, CartHasRecipeQueryService cartHasRecipeQueryService) {
        this.cartHasRecipeService = cartHasRecipeService;
        this.cartHasRecipeQueryService = cartHasRecipeQueryService;
    }

    /**
     * {@code POST  /cart-has-recipes} : Create a new cartHasRecipe.
     *
     * @param cartHasRecipeDTO the cartHasRecipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartHasRecipeDTO, or with status {@code 400 (Bad Request)} if the cartHasRecipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-has-recipes")
    public ResponseEntity<CartHasRecipeDTO> createCartHasRecipe(@Valid @RequestBody CartHasRecipeDTO cartHasRecipeDTO) throws URISyntaxException {
        log.debug("REST request to save CartHasRecipe : {}", cartHasRecipeDTO);
        if (cartHasRecipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cartHasRecipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartHasRecipeDTO result = cartHasRecipeService.save(cartHasRecipeDTO);
        return ResponseEntity.created(new URI("/api/cart-has-recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cart-has-recipes} : Updates an existing cartHasRecipe.
     *
     * @param cartHasRecipeDTO the cartHasRecipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartHasRecipeDTO,
     * or with status {@code 400 (Bad Request)} if the cartHasRecipeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartHasRecipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-has-recipes")
    public ResponseEntity<CartHasRecipeDTO> updateCartHasRecipe(@Valid @RequestBody CartHasRecipeDTO cartHasRecipeDTO) throws URISyntaxException {
        log.debug("REST request to update CartHasRecipe : {}", cartHasRecipeDTO);
        if (cartHasRecipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartHasRecipeDTO result = cartHasRecipeService.save(cartHasRecipeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartHasRecipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cart-has-recipes} : get all the cartHasRecipes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartHasRecipes in body.
     */
    @GetMapping("/cart-has-recipes")
    public ResponseEntity<List<CartHasRecipeDTO>> getAllCartHasRecipes(CartHasRecipeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CartHasRecipes by criteria: {}", criteria);
        Page<CartHasRecipeDTO> page = cartHasRecipeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cart-has-recipes/count} : count all the cartHasRecipes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cart-has-recipes/count")
    public ResponseEntity<Long> countCartHasRecipes(CartHasRecipeCriteria criteria) {
        log.debug("REST request to count CartHasRecipes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cartHasRecipeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cart-has-recipes/:id} : get the "id" cartHasRecipe.
     *
     * @param id the id of the cartHasRecipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartHasRecipeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-has-recipes/{id}")
    public ResponseEntity<CartHasRecipeDTO> getCartHasRecipe(@PathVariable Long id) {
        log.debug("REST request to get CartHasRecipe : {}", id);
        Optional<CartHasRecipeDTO> cartHasRecipeDTO = cartHasRecipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartHasRecipeDTO);
    }

    /**
     * {@code DELETE  /cart-has-recipes/:id} : delete the "id" cartHasRecipe.
     *
     * @param id the id of the cartHasRecipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-has-recipes/{id}")
    public ResponseEntity<Void> deleteCartHasRecipe(@PathVariable Long id) {
        log.debug("REST request to delete CartHasRecipe : {}", id);
        cartHasRecipeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
