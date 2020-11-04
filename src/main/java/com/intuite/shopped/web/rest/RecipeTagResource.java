package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.RecipeTagService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.RecipeTagDTO;
import com.intuite.shopped.service.dto.RecipeTagCriteria;
import com.intuite.shopped.service.RecipeTagQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.RecipeTag}.
 */
@RestController
@RequestMapping("/api")
public class RecipeTagResource {

    private final Logger log = LoggerFactory.getLogger(RecipeTagResource.class);

    private static final String ENTITY_NAME = "recipeTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeTagService recipeTagService;

    private final RecipeTagQueryService recipeTagQueryService;

    public RecipeTagResource(RecipeTagService recipeTagService, RecipeTagQueryService recipeTagQueryService) {
        this.recipeTagService = recipeTagService;
        this.recipeTagQueryService = recipeTagQueryService;
    }

    /**
     * {@code POST  /recipe-tags} : Create a new recipeTag.
     *
     * @param recipeTagDTO the recipeTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeTagDTO, or with status {@code 400 (Bad Request)} if the recipeTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipe-tags")
    public ResponseEntity<RecipeTagDTO> createRecipeTag(@Valid @RequestBody RecipeTagDTO recipeTagDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeTag : {}", recipeTagDTO);
        if (recipeTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeTagDTO result = recipeTagService.save(recipeTagDTO);
        return ResponseEntity.created(new URI("/api/recipe-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipe-tags} : Updates an existing recipeTag.
     *
     * @param recipeTagDTO the recipeTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeTagDTO,
     * or with status {@code 400 (Bad Request)} if the recipeTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipe-tags")
    public ResponseEntity<RecipeTagDTO> updateRecipeTag(@Valid @RequestBody RecipeTagDTO recipeTagDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeTag : {}", recipeTagDTO);
        if (recipeTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeTagDTO result = recipeTagService.save(recipeTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipeTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipe-tags} : get all the recipeTags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeTags in body.
     */
    @GetMapping("/recipe-tags")
    public ResponseEntity<List<RecipeTagDTO>> getAllRecipeTags(RecipeTagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RecipeTags by criteria: {}", criteria);
        Page<RecipeTagDTO> page = recipeTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipe-tags/count} : count all the recipeTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recipe-tags/count")
    public ResponseEntity<Long> countRecipeTags(RecipeTagCriteria criteria) {
        log.debug("REST request to count RecipeTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(recipeTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recipe-tags/:id} : get the "id" recipeTag.
     *
     * @param id the id of the recipeTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipe-tags/{id}")
    public ResponseEntity<RecipeTagDTO> getRecipeTag(@PathVariable Long id) {
        log.debug("REST request to get RecipeTag : {}", id);
        Optional<RecipeTagDTO> recipeTagDTO = recipeTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeTagDTO);
    }

    /**
     * {@code DELETE  /recipe-tags/:id} : delete the "id" recipeTag.
     *
     * @param id the id of the recipeTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipe-tags/{id}")
    public ResponseEntity<Void> deleteRecipeTag(@PathVariable Long id) {
        log.debug("REST request to delete RecipeTag : {}", id);
        recipeTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
