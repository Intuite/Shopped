package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.RecipeSharedService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.RecipeSharedDTO;
import com.intuite.shopped.service.dto.RecipeSharedCriteria;
import com.intuite.shopped.service.RecipeSharedQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.RecipeShared}.
 */
@RestController
@RequestMapping("/api")
public class RecipeSharedResource {

    private final Logger log = LoggerFactory.getLogger(RecipeSharedResource.class);

    private static final String ENTITY_NAME = "recipeShared";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeSharedService recipeSharedService;

    private final RecipeSharedQueryService recipeSharedQueryService;

    public RecipeSharedResource(RecipeSharedService recipeSharedService, RecipeSharedQueryService recipeSharedQueryService) {
        this.recipeSharedService = recipeSharedService;
        this.recipeSharedQueryService = recipeSharedQueryService;
    }

    /**
     * {@code POST  /recipe-shareds} : Create a new recipeShared.
     *
     * @param recipeSharedDTO the recipeSharedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeSharedDTO, or with status {@code 400 (Bad Request)} if the recipeShared has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipe-shareds")
    public ResponseEntity<RecipeSharedDTO> createRecipeShared(@Valid @RequestBody RecipeSharedDTO recipeSharedDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeShared : {}", recipeSharedDTO);
        if (recipeSharedDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeShared cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeSharedDTO result = recipeSharedService.save(recipeSharedDTO);
        return ResponseEntity.created(new URI("/api/recipe-shareds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipe-shareds} : Updates an existing recipeShared.
     *
     * @param recipeSharedDTO the recipeSharedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeSharedDTO,
     * or with status {@code 400 (Bad Request)} if the recipeSharedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeSharedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipe-shareds")
    public ResponseEntity<RecipeSharedDTO> updateRecipeShared(@Valid @RequestBody RecipeSharedDTO recipeSharedDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeShared : {}", recipeSharedDTO);
        if (recipeSharedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeSharedDTO result = recipeSharedService.save(recipeSharedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeSharedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipe-shareds} : get all the recipeShareds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeShareds in body.
     */
    @GetMapping("/recipe-shareds")
    public ResponseEntity<List<RecipeSharedDTO>> getAllRecipeShareds(RecipeSharedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecipeShareds by criteria: {}", criteria);
        Page<RecipeSharedDTO> page = recipeSharedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipe-shareds/count} : count all the recipeShareds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recipe-shareds/count")
    public ResponseEntity<Long> countRecipeShareds(RecipeSharedCriteria criteria) {
        log.debug("REST request to count RecipeShareds by criteria: {}", criteria);
        return ResponseEntity.ok().body(recipeSharedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recipe-shareds/:id} : get the "id" recipeShared.
     *
     * @param id the id of the recipeSharedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeSharedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipe-shareds/{id}")
    public ResponseEntity<RecipeSharedDTO> getRecipeShared(@PathVariable Long id) {
        log.debug("REST request to get RecipeShared : {}", id);
        Optional<RecipeSharedDTO> recipeSharedDTO = recipeSharedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeSharedDTO);
    }

    /**
     * {@code DELETE  /recipe-shareds/:id} : delete the "id" recipeShared.
     *
     * @param id the id of the recipeSharedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipe-shareds/{id}")
    public ResponseEntity<Void> deleteRecipeShared(@PathVariable Long id) {
        log.debug("REST request to delete RecipeShared : {}", id);
        recipeSharedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
