package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.RecipeHasRecipeTagService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagDTO;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagCriteria;
import com.intuite.shopped.service.RecipeHasRecipeTagQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.RecipeHasRecipeTag}.
 */
@RestController
@RequestMapping("/api")
public class RecipeHasRecipeTagResource {

    private final Logger log = LoggerFactory.getLogger(RecipeHasRecipeTagResource.class);

    private static final String ENTITY_NAME = "recipeHasRecipeTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeHasRecipeTagService recipeHasRecipeTagService;

    private final RecipeHasRecipeTagQueryService recipeHasRecipeTagQueryService;

    public RecipeHasRecipeTagResource(RecipeHasRecipeTagService recipeHasRecipeTagService, RecipeHasRecipeTagQueryService recipeHasRecipeTagQueryService) {
        this.recipeHasRecipeTagService = recipeHasRecipeTagService;
        this.recipeHasRecipeTagQueryService = recipeHasRecipeTagQueryService;
    }

    /**
     * {@code POST  /recipe-has-recipe-tags} : Create a new recipeHasRecipeTag.
     *
     * @param recipeHasRecipeTagDTO the recipeHasRecipeTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeHasRecipeTagDTO, or with status {@code 400 (Bad Request)} if the recipeHasRecipeTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipe-has-recipe-tags")
    public ResponseEntity<RecipeHasRecipeTagDTO> createRecipeHasRecipeTag(@Valid @RequestBody RecipeHasRecipeTagDTO recipeHasRecipeTagDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeHasRecipeTag : {}", recipeHasRecipeTagDTO);
        if (recipeHasRecipeTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeHasRecipeTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeHasRecipeTagDTO result = recipeHasRecipeTagService.save(recipeHasRecipeTagDTO);
        return ResponseEntity.created(new URI("/api/recipe-has-recipe-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipe-has-recipe-tags} : Updates an existing recipeHasRecipeTag.
     *
     * @param recipeHasRecipeTagDTO the recipeHasRecipeTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeHasRecipeTagDTO,
     * or with status {@code 400 (Bad Request)} if the recipeHasRecipeTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeHasRecipeTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipe-has-recipe-tags")
    public ResponseEntity<RecipeHasRecipeTagDTO> updateRecipeHasRecipeTag(@Valid @RequestBody RecipeHasRecipeTagDTO recipeHasRecipeTagDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeHasRecipeTag : {}", recipeHasRecipeTagDTO);
        if (recipeHasRecipeTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeHasRecipeTagDTO result = recipeHasRecipeTagService.save(recipeHasRecipeTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeHasRecipeTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipe-has-recipe-tags} : get all the recipeHasRecipeTags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeHasRecipeTags in body.
     */
    @GetMapping("/recipe-has-recipe-tags")
    public ResponseEntity<List<RecipeHasRecipeTagDTO>> getAllRecipeHasRecipeTags(RecipeHasRecipeTagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecipeHasRecipeTags by criteria: {}", criteria);
        Page<RecipeHasRecipeTagDTO> page = recipeHasRecipeTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipe-has-recipe-tags/count} : count all the recipeHasRecipeTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recipe-has-recipe-tags/count")
    public ResponseEntity<Long> countRecipeHasRecipeTags(RecipeHasRecipeTagCriteria criteria) {
        log.debug("REST request to count RecipeHasRecipeTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(recipeHasRecipeTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recipe-has-recipe-tags/:id} : get the "id" recipeHasRecipeTag.
     *
     * @param id the id of the recipeHasRecipeTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeHasRecipeTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipe-has-recipe-tags/{id}")
    public ResponseEntity<RecipeHasRecipeTagDTO> getRecipeHasRecipeTag(@PathVariable Long id) {
        log.debug("REST request to get RecipeHasRecipeTag : {}", id);
        Optional<RecipeHasRecipeTagDTO> recipeHasRecipeTagDTO = recipeHasRecipeTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeHasRecipeTagDTO);
    }

    /**
     * {@code DELETE  /recipe-has-recipe-tags/:id} : delete the "id" recipeHasRecipeTag.
     *
     * @param id the id of the recipeHasRecipeTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipe-has-recipe-tags/{id}")
    public ResponseEntity<Void> deleteRecipeHasRecipeTag(@PathVariable Long id) {
        log.debug("REST request to delete RecipeHasRecipeTag : {}", id);
        recipeHasRecipeTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
