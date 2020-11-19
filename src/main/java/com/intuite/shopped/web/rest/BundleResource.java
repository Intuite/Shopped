package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.BundleService;
import com.intuite.shopped.service.dto.AwardCriteria;
import com.intuite.shopped.service.dto.AwardDTO;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.BundleDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.intuite.shopped.domain.Bundle}.
 */
@RestController
@RequestMapping("/api")
public class BundleResource {

    private final Logger log = LoggerFactory.getLogger(BundleResource.class);

    private static final String ENTITY_NAME = "bundle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BundleService bundleService;

    public BundleResource(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    /**
     * {@code POST  /bundles} : Create a new bundle.
     *
     * @param bundleDTO the bundleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bundleDTO, or with status {@code 400 (Bad Request)} if the bundle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bundles")
    public ResponseEntity<BundleDTO> createBundle(@Valid @RequestBody BundleDTO bundleDTO) throws URISyntaxException {
        log.debug("REST request to save Bundle : {}", bundleDTO);
        if (bundleDTO.getId() != null) {
            throw new BadRequestAlertException("A new bundle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BundleDTO result = bundleService.save(bundleDTO);
        return ResponseEntity.created(new URI("/api/bundles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bundles} : Updates an existing bundle.
     *
     * @param bundleDTO the bundleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bundleDTO,
     * or with status {@code 400 (Bad Request)} if the bundleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bundleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bundles")
    public ResponseEntity<BundleDTO> updateBundle(@Valid @RequestBody BundleDTO bundleDTO) throws URISyntaxException {
        log.debug("REST request to update Bundle : {}", bundleDTO);
        if (bundleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BundleDTO result = bundleService.save(bundleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bundleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bundles} : get all the bundles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bundles in body.
     */
    @GetMapping("/bundles")
    public List<BundleDTO> getAllBundles() {
        log.debug("REST request to get all Bundles");
        return bundleService.findAll();
    }

    /**
     * {@code GET  /bundles/:id} : get the "id" bundle.
     *
     * @param id the id of the bundleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bundleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bundles/{id}")
    public ResponseEntity<BundleDTO> getBundle(@PathVariable Long id) {
        log.debug("REST request to get Bundle : {}", id);
        Optional<BundleDTO> bundleDTO = bundleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bundleDTO);
    }

    /**
     * {@code DELETE  /bundles/:id} : delete the "id" bundle.
     *
     * @param id the id of the bundleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bundles/{id}")
    public ResponseEntity<Void> deleteBundle(@PathVariable Long id) {
        log.debug("REST request to delete Bundle : {}", id);
        bundleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
