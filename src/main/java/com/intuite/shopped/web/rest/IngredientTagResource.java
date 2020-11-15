package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.IngredientTagQueryService;
import com.intuite.shopped.service.IngredientTagService;
import com.intuite.shopped.service.dto.IngredientTagCriteria;
import com.intuite.shopped.service.dto.IngredientTagDTO;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.intuite.shopped.domain.IngredientTag}.
 */
@RestController
@RequestMapping("/api")
public class IngredientTagResource {

    private static final String ENTITY_NAME = "ingredientTag";
    private final Logger log = LoggerFactory.getLogger(IngredientTagResource.class);
    private final IngredientTagService ingredientTagService;
    private final IngredientTagQueryService ingredientTagQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public IngredientTagResource(IngredientTagService ingredientTagService, IngredientTagQueryService ingredientTagQueryService) {
        this.ingredientTagService = ingredientTagService;
        this.ingredientTagQueryService = ingredientTagQueryService;
    }

    /**
     * {@code POST  /ingredient-tags} : Create a new ingredientTag.
     *
     * @param ingredientTagDTO the ingredientTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientTagDTO, or with status {@code 400 (Bad Request)} if the ingredientTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredient-tags")
    public ResponseEntity<IngredientTagDTO> createIngredientTag(@Valid @RequestBody IngredientTagDTO ingredientTagDTO) throws URISyntaxException {
        log.debug("REST request to save IngredientTag : {}", ingredientTagDTO);
        if (ingredientTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredientTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientTagDTO result = ingredientTagService.save(ingredientTagDTO);
        return ResponseEntity.created(new URI("/api/ingredient-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredient-tags} : Updates an existing ingredientTag.
     *
     * @param ingredientTagDTO the ingredientTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientTagDTO,
     * or with status {@code 400 (Bad Request)} if the ingredientTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredient-tags")
    public ResponseEntity<IngredientTagDTO> updateIngredientTag(@Valid @RequestBody IngredientTagDTO ingredientTagDTO) throws URISyntaxException {
        log.debug("REST request to update IngredientTag : {}", ingredientTagDTO);
        if (ingredientTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientTagDTO result = ingredientTagService.save(ingredientTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingredientTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ingredient-tags} : get all the ingredientTags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientTags in body.
     */
    @GetMapping("/ingredient-tags")
    public ResponseEntity<List<IngredientTagDTO>> getAllIngredientTags(IngredientTagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IngredientTags by criteria: {}", criteria);
        Page<IngredientTagDTO> page = ingredientTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredient-tags} : get all the ingredientTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientTags in body.
     */
    @GetMapping("/ingredient-tags/all")
    public ResponseEntity<List<IngredientTagDTO>> getAllingredientTags(IngredientTagCriteria criteria) {
        log.debug("REST request to get ingredientTags by criteria: {}", criteria);
        List<IngredientTagDTO> list = ingredientTagQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /ingredient-tags/count} : count all the ingredientTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ingredient-tags/count")
    public ResponseEntity<Long> countIngredientTags(IngredientTagCriteria criteria) {
        log.debug("REST request to count IngredientTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(ingredientTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ingredient-tags/:id} : get the "id" ingredientTag.
     *
     * @param id the id of the ingredientTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredient-tags/{id}")
    public ResponseEntity<IngredientTagDTO> getIngredientTag(@PathVariable Long id) {
        log.debug("REST request to get IngredientTag : {}", id);
        Optional<IngredientTagDTO> ingredientTagDTO = ingredientTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientTagDTO);
    }

    /**
     * {@code DELETE  /ingredient-tags/:id} : delete the "id" ingredientTag.
     *
     * @param id the id of the ingredientTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredient-tags/{id}")
    public ResponseEntity<Void> deleteIngredientTag(@PathVariable Long id) {
        log.debug("REST request to delete IngredientTag : {}", id);
        ingredientTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
