package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.AwardService;
import com.intuite.shopped.service.dto.RecipeTagCriteria;
import com.intuite.shopped.service.dto.RecipeTagDTO;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.AwardDTO;
import com.intuite.shopped.service.dto.AwardCriteria;
import com.intuite.shopped.service.AwardQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Award}.
 */
@RestController
@RequestMapping("/api")
public class AwardResource {

    private final Logger log = LoggerFactory.getLogger(AwardResource.class);

    private static final String ENTITY_NAME = "award";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AwardService awardService;

    private final AwardQueryService awardQueryService;

    public AwardResource(AwardService awardService, AwardQueryService awardQueryService) {
        this.awardService = awardService;
        this.awardQueryService = awardQueryService;
    }

    /**
     * {@code POST  /awards} : Create a new award.
     *
     * @param awardDTO the awardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new awardDTO, or with status {@code 400 (Bad Request)} if the award has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/awards")
    public ResponseEntity<AwardDTO> createAward(@Valid @RequestBody AwardDTO awardDTO) throws URISyntaxException {
        log.debug("REST request to save Award : {}", awardDTO);
        if (awardDTO.getId() != null) {
            throw new BadRequestAlertException("A new award cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AwardDTO result = awardService.save(awardDTO);
        return ResponseEntity.created(new URI("/api/awards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /awards} : Updates an existing award.
     *
     * @param awardDTO the awardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated awardDTO,
     * or with status {@code 400 (Bad Request)} if the awardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the awardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/awards")
    public ResponseEntity<AwardDTO> updateAward(@Valid @RequestBody AwardDTO awardDTO) throws URISyntaxException {
        log.debug("REST request to update Award : {}", awardDTO);
        if (awardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AwardDTO result = awardService.save(awardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, awardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /awards} : get all the awards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of awards in body.
     */
    @GetMapping("/awards")
    public ResponseEntity<List<AwardDTO>> getAllAwards(AwardCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Awards by criteria: {}", criteria);
        Page<AwardDTO> page = awardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /awards} : get all the recipeTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of awards in body.
     */
    @GetMapping("/awards/all")
    public ResponseEntity<List<AwardDTO>> getAllAwards(AwardCriteria criteria) {
        log.debug("REST request to get Awards by criteria: {}", criteria);
        List<AwardDTO> list = awardQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /awards/count} : count all the awards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/awards/count")
    public ResponseEntity<Long> countAwards(AwardCriteria criteria) {
        log.debug("REST request to count Awards by criteria: {}", criteria);
        return ResponseEntity.ok().body(awardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /awards/:id} : get the "id" award.
     *
     * @param id the id of the awardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the awardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/awards/{id}")
    public ResponseEntity<AwardDTO> getAward(@PathVariable Long id) {
        log.debug("REST request to get Award : {}", id);
        Optional<AwardDTO> awardDTO = awardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(awardDTO);
    }

    /**
     * (@code GET /awards/post/:id) : get the awards from a post id.
     *
     * @param id the id of the postDTO to retrive the awards.
     * @return the (@link ResponseEntity) with status {@code 200 (OK)} and with body the awardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/awards/post/{id}")
    public ResponseEntity<List<AwardDTO>> getByPost(@PathVariable Long id) {
        log.debug("REST request to get Awards by Post : {}", id);
//        Optional<List<AwardDTO>> list = awardService.findAll();
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, id.toString(),"not implemented")).build();
    }
    /**
     * {@code DELETE  /awards/:id} : delete the "id" award.
     *
     * @param id the id of the awardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/awards/{id}")
    public ResponseEntity<Void> deleteAward(@PathVariable Long id) {
        log.debug("REST request to delete Award : {}", id);
        awardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
