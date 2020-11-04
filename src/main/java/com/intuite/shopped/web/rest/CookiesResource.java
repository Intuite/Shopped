package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.CookiesService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.CookiesDTO;
import com.intuite.shopped.service.dto.CookiesCriteria;
import com.intuite.shopped.service.CookiesQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Cookies}.
 */
@RestController
@RequestMapping("/api")
public class CookiesResource {

    private final Logger log = LoggerFactory.getLogger(CookiesResource.class);

    private static final String ENTITY_NAME = "cookies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CookiesService cookiesService;

    private final CookiesQueryService cookiesQueryService;

    public CookiesResource(CookiesService cookiesService, CookiesQueryService cookiesQueryService) {
        this.cookiesService = cookiesService;
        this.cookiesQueryService = cookiesQueryService;
    }

    /**
     * {@code POST  /cookies} : Create a new cookies.
     *
     * @param cookiesDTO the cookiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cookiesDTO, or with status {@code 400 (Bad Request)} if the cookies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cookies")
    public ResponseEntity<CookiesDTO> createCookies(@Valid @RequestBody CookiesDTO cookiesDTO) throws URISyntaxException {
        log.debug("REST request to save Cookies : {}", cookiesDTO);
        if (cookiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cookies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CookiesDTO result = cookiesService.save(cookiesDTO);
        return ResponseEntity.created(new URI("/api/cookies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cookies} : Updates an existing cookies.
     *
     * @param cookiesDTO the cookiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cookiesDTO,
     * or with status {@code 400 (Bad Request)} if the cookiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cookiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cookies")
    public ResponseEntity<CookiesDTO> updateCookies(@Valid @RequestBody CookiesDTO cookiesDTO) throws URISyntaxException {
        log.debug("REST request to update Cookies : {}", cookiesDTO);
        if (cookiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CookiesDTO result = cookiesService.save(cookiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cookiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cookies} : get all the cookies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cookies in body.
     */
    @GetMapping("/cookies")
    public ResponseEntity<List<CookiesDTO>> getAllCookies(CookiesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cookies by criteria: {}", criteria);
        Page<CookiesDTO> page = cookiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cookies/count} : count all the cookies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cookies/count")
    public ResponseEntity<Long> countCookies(CookiesCriteria criteria) {
        log.debug("REST request to count Cookies by criteria: {}", criteria);
        return ResponseEntity.ok().body(cookiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cookies/:id} : get the "id" cookies.
     *
     * @param id the id of the cookiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cookiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cookies/{id}")
    public ResponseEntity<CookiesDTO> getCookies(@PathVariable Long id) {
        log.debug("REST request to get Cookies : {}", id);
        Optional<CookiesDTO> cookiesDTO = cookiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cookiesDTO);
    }

    /**
     * {@code DELETE  /cookies/:id} : delete the "id" cookies.
     *
     * @param id the id of the cookiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cookies/{id}")
    public ResponseEntity<Void> deleteCookies(@PathVariable Long id) {
        log.debug("REST request to delete Cookies : {}", id);
        cookiesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
