package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.RecipeHasIngredientService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.RecipeHasIngredientDTO;
import com.intuite.shopped.service.dto.RecipeHasIngredientCriteria;
import com.intuite.shopped.service.RecipeHasIngredientQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.RecipeHasIngredient}.
 */
@RestController
@RequestMapping("/api")
public class RecipeHasIngredientResource {

    private final Logger log = LoggerFactory.getLogger(RecipeHasIngredientResource.class);

    private static final String ENTITY_NAME = "recipeHasIngredient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeHasIngredientService recipeHasIngredientService;

    private final RecipeHasIngredientQueryService recipeHasIngredientQueryService;

    public RecipeHasIngredientResource(RecipeHasIngredientService recipeHasIngredientService, RecipeHasIngredientQueryService recipeHasIngredientQueryService) {
        this.recipeHasIngredientService = recipeHasIngredientService;
        this.recipeHasIngredientQueryService = recipeHasIngredientQueryService;
    }

    /**
     * {@code POST  /recipe-has-ingredients} : Create a new recipeHasIngredient.
     *
     * @param recipeHasIngredientDTO the recipeHasIngredientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeHasIngredientDTO, or with status {@code 400 (Bad Request)} if the recipeHasIngredient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipe-has-ingredients")
    public ResponseEntity<RecipeHasIngredientDTO> createRecipeHasIngredient(@Valid @RequestBody RecipeHasIngredientDTO recipeHasIngredientDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeHasIngredient : {}", recipeHasIngredientDTO);
        if (recipeHasIngredientDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeHasIngredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeHasIngredientDTO result = recipeHasIngredientService.save(recipeHasIngredientDTO);
        return ResponseEntity.created(new URI("/api/recipe-has-ingredients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipe-has-ingredients} : Updates an existing recipeHasIngredient.
     *
     * @param recipeHasIngredientDTO the recipeHasIngredientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeHasIngredientDTO,
     * or with status {@code 400 (Bad Request)} if the recipeHasIngredientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeHasIngredientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipe-has-ingredients")
    public ResponseEntity<RecipeHasIngredientDTO> updateRecipeHasIngredient(@Valid @RequestBody RecipeHasIngredientDTO recipeHasIngredientDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeHasIngredient : {}", recipeHasIngredientDTO);
        if (recipeHasIngredientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeHasIngredientDTO result = recipeHasIngredientService.save(recipeHasIngredientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeHasIngredientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipe-has-ingredients} : get all the recipeHasIngredients.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeHasIngredients in body.
     */
    @GetMapping("/recipe-has-ingredients")
    public ResponseEntity<List<RecipeHasIngredientDTO>> getAllRecipeHasIngredients(RecipeHasIngredientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecipeHasIngredients by criteria: {}", criteria);
        Page<RecipeHasIngredientDTO> page = recipeHasIngredientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipe-has-ingredients/count} : count all the recipeHasIngredients.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recipe-has-ingredients/count")
    public ResponseEntity<Long> countRecipeHasIngredients(RecipeHasIngredientCriteria criteria) {
        log.debug("REST request to count RecipeHasIngredients by criteria: {}", criteria);
        return ResponseEntity.ok().body(recipeHasIngredientQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recipe-has-ingredients/:id} : get the "id" recipeHasIngredient.
     *
     * @param id the id of the recipeHasIngredientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeHasIngredientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipe-has-ingredients/{id}")
    public ResponseEntity<RecipeHasIngredientDTO> getRecipeHasIngredient(@PathVariable Long id) {
        log.debug("REST request to get RecipeHasIngredient : {}", id);
        Optional<RecipeHasIngredientDTO> recipeHasIngredientDTO = recipeHasIngredientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeHasIngredientDTO);
    }

    /**
     * {@code DELETE  /recipe-has-ingredients/:id} : delete the "id" recipeHasIngredient.
     *
     * @param id the id of the recipeHasIngredientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipe-has-ingredients/{id}")
    public ResponseEntity<Void> deleteRecipeHasIngredient(@PathVariable Long id) {
        log.debug("REST request to delete RecipeHasIngredient : {}", id);
        recipeHasIngredientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
