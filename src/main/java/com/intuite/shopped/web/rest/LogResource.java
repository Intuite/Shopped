package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.LogService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.LogDTO;
import com.intuite.shopped.service.dto.LogCriteria;
import com.intuite.shopped.service.LogQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Log}.
 */
@RestController
@RequestMapping("/api")
public class LogResource {

    private final Logger log = LoggerFactory.getLogger(LogResource.class);

    private static final String ENTITY_NAME = "log";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogService logService;

    private final LogQueryService logQueryService;

    public LogResource(LogService logService, LogQueryService logQueryService) {
        this.logService = logService;
        this.logQueryService = logQueryService;
    }

    /**
     * {@code POST  /logs} : Create a new log.
     *
     * @param logDTO the logDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logDTO, or with status {@code 400 (Bad Request)} if the log has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logs")
    public ResponseEntity<LogDTO> createLog(@Valid @RequestBody LogDTO logDTO) throws URISyntaxException {
        log.debug("REST request to save Log : {}", logDTO);
        if (logDTO.getId() != null) {
            throw new BadRequestAlertException("A new log cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogDTO result = logService.save(logDTO);
        return ResponseEntity.created(new URI("/api/logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logs} : Updates an existing log.
     *
     * @param logDTO the logDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logDTO,
     * or with status {@code 400 (Bad Request)} if the logDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logs")
    public ResponseEntity<LogDTO> updateLog(@Valid @RequestBody LogDTO logDTO) throws URISyntaxException {
        log.debug("REST request to update Log : {}", logDTO);
        if (logDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogDTO result = logService.save(logDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /logs} : get all the logs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logs in body.
     */
    @GetMapping("/logs")
    public ResponseEntity<List<LogDTO>> getAllLogs(LogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Logs by criteria: {}", criteria);
        Page<LogDTO> page = logQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /logs/count} : count all the logs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/logs/count")
    public ResponseEntity<Long> countLogs(LogCriteria criteria) {
        log.debug("REST request to count Logs by criteria: {}", criteria);
        return ResponseEntity.ok().body(logQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /logs/:id} : get the "id" log.
     *
     * @param id the id of the logDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logs/{id}")
    public ResponseEntity<LogDTO> getLog(@PathVariable Long id) {
        log.debug("REST request to get Log : {}", id);
        Optional<LogDTO> logDTO = logService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logDTO);
    }

    /**
     * {@code DELETE  /logs/:id} : delete the "id" log.
     *
     * @param id the id of the logDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        log.debug("REST request to delete Log : {}", id);
        logService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
