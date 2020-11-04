package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.IngredientHasIngredientTagService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagDTO;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagCriteria;
import com.intuite.shopped.service.IngredientHasIngredientTagQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.IngredientHasIngredientTag}.
 */
@RestController
@RequestMapping("/api")
public class IngredientHasIngredientTagResource {

    private final Logger log = LoggerFactory.getLogger(IngredientHasIngredientTagResource.class);

    private static final String ENTITY_NAME = "ingredientHasIngredientTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientHasIngredientTagService ingredientHasIngredientTagService;

    private final IngredientHasIngredientTagQueryService ingredientHasIngredientTagQueryService;

    public IngredientHasIngredientTagResource(IngredientHasIngredientTagService ingredientHasIngredientTagService, IngredientHasIngredientTagQueryService ingredientHasIngredientTagQueryService) {
        this.ingredientHasIngredientTagService = ingredientHasIngredientTagService;
        this.ingredientHasIngredientTagQueryService = ingredientHasIngredientTagQueryService;
    }

    /**
     * {@code POST  /ingredient-has-ingredient-tags} : Create a new ingredientHasIngredientTag.
     *
     * @param ingredientHasIngredientTagDTO the ingredientHasIngredientTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientHasIngredientTagDTO, or with status {@code 400 (Bad Request)} if the ingredientHasIngredientTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredient-has-ingredient-tags")
    public ResponseEntity<IngredientHasIngredientTagDTO> createIngredientHasIngredientTag(@Valid @RequestBody IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO) throws URISyntaxException {
        log.debug("REST request to save IngredientHasIngredientTag : {}", ingredientHasIngredientTagDTO);
        if (ingredientHasIngredientTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredientHasIngredientTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientHasIngredientTagDTO result = ingredientHasIngredientTagService.save(ingredientHasIngredientTagDTO);
        return ResponseEntity.created(new URI("/api/ingredient-has-ingredient-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredient-has-ingredient-tags} : Updates an existing ingredientHasIngredientTag.
     *
     * @param ingredientHasIngredientTagDTO the ingredientHasIngredientTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientHasIngredientTagDTO,
     * or with status {@code 400 (Bad Request)} if the ingredientHasIngredientTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientHasIngredientTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredient-has-ingredient-tags")
    public ResponseEntity<IngredientHasIngredientTagDTO> updateIngredientHasIngredientTag(@Valid @RequestBody IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO) throws URISyntaxException {
        log.debug("REST request to update IngredientHasIngredientTag : {}", ingredientHasIngredientTagDTO);
        if (ingredientHasIngredientTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientHasIngredientTagDTO result = ingredientHasIngredientTagService.save(ingredientHasIngredientTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingredientHasIngredientTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ingredient-has-ingredient-tags} : get all the ingredientHasIngredientTags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientHasIngredientTags in body.
     */
    @GetMapping("/ingredient-has-ingredient-tags")
    public ResponseEntity<List<IngredientHasIngredientTagDTO>> getAllIngredientHasIngredientTags(IngredientHasIngredientTagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IngredientHasIngredientTags by criteria: {}", criteria);
        Page<IngredientHasIngredientTagDTO> page = ingredientHasIngredientTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredient-has-ingredient-tags/count} : count all the ingredientHasIngredientTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ingredient-has-ingredient-tags/count")
    public ResponseEntity<Long> countIngredientHasIngredientTags(IngredientHasIngredientTagCriteria criteria) {
        log.debug("REST request to count IngredientHasIngredientTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(ingredientHasIngredientTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ingredient-has-ingredient-tags/:id} : get the "id" ingredientHasIngredientTag.
     *
     * @param id the id of the ingredientHasIngredientTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientHasIngredientTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredient-has-ingredient-tags/{id}")
    public ResponseEntity<IngredientHasIngredientTagDTO> getIngredientHasIngredientTag(@PathVariable Long id) {
        log.debug("REST request to get IngredientHasIngredientTag : {}", id);
        Optional<IngredientHasIngredientTagDTO> ingredientHasIngredientTagDTO = ingredientHasIngredientTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientHasIngredientTagDTO);
    }

    /**
     * {@code DELETE  /ingredient-has-ingredient-tags/:id} : delete the "id" ingredientHasIngredientTag.
     *
     * @param id the id of the ingredientHasIngredientTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredient-has-ingredient-tags/{id}")
    public ResponseEntity<Void> deleteIngredientHasIngredientTag(@PathVariable Long id) {
        log.debug("REST request to delete IngredientHasIngredientTag : {}", id);
        ingredientHasIngredientTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
