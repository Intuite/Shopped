package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.CommendationService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.CommendationDTO;
import com.intuite.shopped.service.dto.CommendationCriteria;
import com.intuite.shopped.service.CommendationQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Commendation}.
 */
@RestController
@RequestMapping("/api")
public class CommendationResource {

    private final Logger log = LoggerFactory.getLogger(CommendationResource.class);

    private static final String ENTITY_NAME = "commendation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommendationService commendationService;

    private final CommendationQueryService commendationQueryService;

    public CommendationResource(CommendationService commendationService, CommendationQueryService commendationQueryService) {
        this.commendationService = commendationService;
        this.commendationQueryService = commendationQueryService;
    }

    /**
     * {@code POST  /commendations} : Create a new commendation.
     *
     * @param commendationDTO the commendationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commendationDTO, or with status {@code 400 (Bad Request)} if the commendation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commendations")
    public ResponseEntity<CommendationDTO> createCommendation(@Valid @RequestBody CommendationDTO commendationDTO) throws URISyntaxException {
        log.debug("REST request to save Commendation : {}", commendationDTO);
        if (commendationDTO.getId() != null) {
            throw new BadRequestAlertException("A new commendation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommendationDTO result = commendationService.save(commendationDTO);
        return ResponseEntity.created(new URI("/api/commendations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commendations} : Updates an existing commendation.
     *
     * @param commendationDTO the commendationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commendationDTO,
     * or with status {@code 400 (Bad Request)} if the commendationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commendationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commendations")
    public ResponseEntity<CommendationDTO> updateCommendation(@Valid @RequestBody CommendationDTO commendationDTO) throws URISyntaxException {
        log.debug("REST request to update Commendation : {}", commendationDTO);
        if (commendationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommendationDTO result = commendationService.save(commendationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commendationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commendations} : get all the commendations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commendations in body.
     */
    @GetMapping("/commendations")
    public ResponseEntity<List<CommendationDTO>> getAllCommendations(CommendationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commendations by criteria: {}", criteria);
        Page<CommendationDTO> page = commendationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commendations/count} : count all the commendations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commendations/count")
    public ResponseEntity<Long> countCommendations(CommendationCriteria criteria) {
        log.debug("REST request to count Commendations by criteria: {}", criteria);
        return ResponseEntity.ok().body(commendationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commendations/:id} : get the "id" commendation.
     *
     * @param id the id of the commendationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commendationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commendations/{id}")
    public ResponseEntity<CommendationDTO> getCommendation(@PathVariable Long id) {
        log.debug("REST request to get Commendation : {}", id);
        Optional<CommendationDTO> commendationDTO = commendationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commendationDTO);
    }

    /**
     * {@code DELETE  /commendations/:id} : delete the "id" commendation.
     *
     * @param id the id of the commendationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commendations/{id}")
    public ResponseEntity<Void> deleteCommendation(@PathVariable Long id) {
        log.debug("REST request to delete Commendation : {}", id);
        commendationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
