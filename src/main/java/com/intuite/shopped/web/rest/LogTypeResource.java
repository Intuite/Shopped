package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.LogTypeService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.LogTypeDTO;
import com.intuite.shopped.service.dto.LogTypeCriteria;
import com.intuite.shopped.service.LogTypeQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.LogType}.
 */
@RestController
@RequestMapping("/api")
public class LogTypeResource {

    private final Logger log = LoggerFactory.getLogger(LogTypeResource.class);

    private static final String ENTITY_NAME = "logType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogTypeService logTypeService;

    private final LogTypeQueryService logTypeQueryService;

    public LogTypeResource(LogTypeService logTypeService, LogTypeQueryService logTypeQueryService) {
        this.logTypeService = logTypeService;
        this.logTypeQueryService = logTypeQueryService;
    }

    /**
     * {@code POST  /log-types} : Create a new logType.
     *
     * @param logTypeDTO the logTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logTypeDTO, or with status {@code 400 (Bad Request)} if the logType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-types")
    public ResponseEntity<LogTypeDTO> createLogType(@Valid @RequestBody LogTypeDTO logTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LogType : {}", logTypeDTO);
        if (logTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new logType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogTypeDTO result = logTypeService.save(logTypeDTO);
        return ResponseEntity.created(new URI("/api/log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-types} : Updates an existing logType.
     *
     * @param logTypeDTO the logTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logTypeDTO,
     * or with status {@code 400 (Bad Request)} if the logTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-types")
    public ResponseEntity<LogTypeDTO> updateLogType(@Valid @RequestBody LogTypeDTO logTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LogType : {}", logTypeDTO);
        if (logTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogTypeDTO result = logTypeService.save(logTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /log-types} : get all the logTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logTypes in body.
     */
    @GetMapping("/log-types")
    public ResponseEntity<List<LogTypeDTO>> getAllLogTypes(LogTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LogTypes by criteria: {}", criteria);
        Page<LogTypeDTO> page = logTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-types/count} : count all the logTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/log-types/count")
    public ResponseEntity<Long> countLogTypes(LogTypeCriteria criteria) {
        log.debug("REST request to count LogTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(logTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /log-types/:id} : get the "id" logType.
     *
     * @param id the id of the logTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-types/{id}")
    public ResponseEntity<LogTypeDTO> getLogType(@PathVariable Long id) {
        log.debug("REST request to get LogType : {}", id);
        Optional<LogTypeDTO> logTypeDTO = logTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logTypeDTO);
    }

    /**
     * {@code DELETE  /log-types/:id} : delete the "id" logType.
     *
     * @param id the id of the logTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-types/{id}")
    public ResponseEntity<Void> deleteLogType(@PathVariable Long id) {
        log.debug("REST request to delete LogType : {}", id);
        logTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
