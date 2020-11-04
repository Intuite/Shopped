package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.CollectionHasRecipeService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.CollectionHasRecipeDTO;
import com.intuite.shopped.service.dto.CollectionHasRecipeCriteria;
import com.intuite.shopped.service.CollectionHasRecipeQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.CollectionHasRecipe}.
 */
@RestController
@RequestMapping("/api")
public class CollectionHasRecipeResource {

    private final Logger log = LoggerFactory.getLogger(CollectionHasRecipeResource.class);

    private static final String ENTITY_NAME = "collectionHasRecipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionHasRecipeService collectionHasRecipeService;

    private final CollectionHasRecipeQueryService collectionHasRecipeQueryService;

    public CollectionHasRecipeResource(CollectionHasRecipeService collectionHasRecipeService, CollectionHasRecipeQueryService collectionHasRecipeQueryService) {
        this.collectionHasRecipeService = collectionHasRecipeService;
        this.collectionHasRecipeQueryService = collectionHasRecipeQueryService;
    }

    /**
     * {@code POST  /collection-has-recipes} : Create a new collectionHasRecipe.
     *
     * @param collectionHasRecipeDTO the collectionHasRecipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionHasRecipeDTO, or with status {@code 400 (Bad Request)} if the collectionHasRecipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collection-has-recipes")
    public ResponseEntity<CollectionHasRecipeDTO> createCollectionHasRecipe(@Valid @RequestBody CollectionHasRecipeDTO collectionHasRecipeDTO) throws URISyntaxException {
        log.debug("REST request to save CollectionHasRecipe : {}", collectionHasRecipeDTO);
        if (collectionHasRecipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new collectionHasRecipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollectionHasRecipeDTO result = collectionHasRecipeService.save(collectionHasRecipeDTO);
        return ResponseEntity.created(new URI("/api/collection-has-recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collection-has-recipes} : Updates an existing collectionHasRecipe.
     *
     * @param collectionHasRecipeDTO the collectionHasRecipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionHasRecipeDTO,
     * or with status {@code 400 (Bad Request)} if the collectionHasRecipeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionHasRecipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collection-has-recipes")
    public ResponseEntity<CollectionHasRecipeDTO> updateCollectionHasRecipe(@Valid @RequestBody CollectionHasRecipeDTO collectionHasRecipeDTO) throws URISyntaxException {
        log.debug("REST request to update CollectionHasRecipe : {}", collectionHasRecipeDTO);
        if (collectionHasRecipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollectionHasRecipeDTO result = collectionHasRecipeService.save(collectionHasRecipeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionHasRecipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collection-has-recipes} : get all the collectionHasRecipes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectionHasRecipes in body.
     */
    @GetMapping("/collection-has-recipes")
    public ResponseEntity<List<CollectionHasRecipeDTO>> getAllCollectionHasRecipes(CollectionHasRecipeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CollectionHasRecipes by criteria: {}", criteria);
        Page<CollectionHasRecipeDTO> page = collectionHasRecipeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collection-has-recipes/count} : count all the collectionHasRecipes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/collection-has-recipes/count")
    public ResponseEntity<Long> countCollectionHasRecipes(CollectionHasRecipeCriteria criteria) {
        log.debug("REST request to count CollectionHasRecipes by criteria: {}", criteria);
        return ResponseEntity.ok().body(collectionHasRecipeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /collection-has-recipes/:id} : get the "id" collectionHasRecipe.
     *
     * @param id the id of the collectionHasRecipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionHasRecipeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collection-has-recipes/{id}")
    public ResponseEntity<CollectionHasRecipeDTO> getCollectionHasRecipe(@PathVariable Long id) {
        log.debug("REST request to get CollectionHasRecipe : {}", id);
        Optional<CollectionHasRecipeDTO> collectionHasRecipeDTO = collectionHasRecipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collectionHasRecipeDTO);
    }

    /**
     * {@code DELETE  /collection-has-recipes/:id} : delete the "id" collectionHasRecipe.
     *
     * @param id the id of the collectionHasRecipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collection-has-recipes/{id}")
    public ResponseEntity<Void> deleteCollectionHasRecipe(@PathVariable Long id) {
        log.debug("REST request to delete CollectionHasRecipe : {}", id);
        collectionHasRecipeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
