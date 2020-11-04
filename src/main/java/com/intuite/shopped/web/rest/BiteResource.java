package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.BiteService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.BiteDTO;
import com.intuite.shopped.service.dto.BiteCriteria;
import com.intuite.shopped.service.BiteQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Bite}.
 */
@RestController
@RequestMapping("/api")
public class BiteResource {

    private final Logger log = LoggerFactory.getLogger(BiteResource.class);

    private static final String ENTITY_NAME = "bite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BiteService biteService;

    private final BiteQueryService biteQueryService;

    public BiteResource(BiteService biteService, BiteQueryService biteQueryService) {
        this.biteService = biteService;
        this.biteQueryService = biteQueryService;
    }

    /**
     * {@code POST  /bites} : Create a new bite.
     *
     * @param biteDTO the biteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new biteDTO, or with status {@code 400 (Bad Request)} if the bite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bites")
    public ResponseEntity<BiteDTO> createBite(@Valid @RequestBody BiteDTO biteDTO) throws URISyntaxException {
        log.debug("REST request to save Bite : {}", biteDTO);
        if (biteDTO.getId() != null) {
            throw new BadRequestAlertException("A new bite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BiteDTO result = biteService.save(biteDTO);
        return ResponseEntity.created(new URI("/api/bites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bites} : Updates an existing bite.
     *
     * @param biteDTO the biteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated biteDTO,
     * or with status {@code 400 (Bad Request)} if the biteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the biteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bites")
    public ResponseEntity<BiteDTO> updateBite(@Valid @RequestBody BiteDTO biteDTO) throws URISyntaxException {
        log.debug("REST request to update Bite : {}", biteDTO);
        if (biteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BiteDTO result = biteService.save(biteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, biteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bites} : get all the bites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bites in body.
     */
    @GetMapping("/bites")
    public ResponseEntity<List<BiteDTO>> getAllBites(BiteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bites by criteria: {}", criteria);
        Page<BiteDTO> page = biteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bites/count} : count all the bites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bites/count")
    public ResponseEntity<Long> countBites(BiteCriteria criteria) {
        log.debug("REST request to count Bites by criteria: {}", criteria);
        return ResponseEntity.ok().body(biteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bites/:id} : get the "id" bite.
     *
     * @param id the id of the biteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the biteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bites/{id}")
    public ResponseEntity<BiteDTO> getBite(@PathVariable Long id) {
        log.debug("REST request to get Bite : {}", id);
        Optional<BiteDTO> biteDTO = biteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(biteDTO);
    }

    /**
     * {@code DELETE  /bites/:id} : delete the "id" bite.
     *
     * @param id the id of the biteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bites/{id}")
    public ResponseEntity<Void> deleteBite(@PathVariable Long id) {
        log.debug("REST request to delete Bite : {}", id);
        biteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
