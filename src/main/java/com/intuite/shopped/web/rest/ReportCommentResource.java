package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.ReportCommentService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.ReportCommentDTO;
import com.intuite.shopped.service.dto.ReportCommentCriteria;
import com.intuite.shopped.service.ReportCommentQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.ReportComment}.
 */
@RestController
@RequestMapping("/api")
public class ReportCommentResource {

    private final Logger log = LoggerFactory.getLogger(ReportCommentResource.class);

    private static final String ENTITY_NAME = "reportComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportCommentService reportCommentService;

    private final ReportCommentQueryService reportCommentQueryService;

    public ReportCommentResource(ReportCommentService reportCommentService, ReportCommentQueryService reportCommentQueryService) {
        this.reportCommentService = reportCommentService;
        this.reportCommentQueryService = reportCommentQueryService;
    }

    /**
     * {@code POST  /report-comments} : Create a new reportComment.
     *
     * @param reportCommentDTO the reportCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportCommentDTO, or with status {@code 400 (Bad Request)} if the reportComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-comments")
    public ResponseEntity<ReportCommentDTO> createReportComment(@Valid @RequestBody ReportCommentDTO reportCommentDTO) throws URISyntaxException {
        log.debug("REST request to save ReportComment : {}", reportCommentDTO);
        if (reportCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportCommentDTO result = reportCommentService.save(reportCommentDTO);
        return ResponseEntity.created(new URI("/api/report-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-comments} : Updates an existing reportComment.
     *
     * @param reportCommentDTO the reportCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportCommentDTO,
     * or with status {@code 400 (Bad Request)} if the reportCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-comments")
    public ResponseEntity<ReportCommentDTO> updateReportComment(@Valid @RequestBody ReportCommentDTO reportCommentDTO) throws URISyntaxException {
        log.debug("REST request to update ReportComment : {}", reportCommentDTO);
        if (reportCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportCommentDTO result = reportCommentService.save(reportCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-comments} : get all the reportComments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportComments in body.
     */
    @GetMapping("/report-comments")
    public ResponseEntity<List<ReportCommentDTO>> getAllReportComments(ReportCommentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReportComments by criteria: {}", criteria);
        Page<ReportCommentDTO> page = reportCommentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-comments/count} : count all the reportComments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/report-comments/count")
    public ResponseEntity<Long> countReportComments(ReportCommentCriteria criteria) {
        log.debug("REST request to count ReportComments by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportCommentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /report-comments/:id} : get the "id" reportComment.
     *
     * @param id the id of the reportCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-comments/{id}")
    public ResponseEntity<ReportCommentDTO> getReportComment(@PathVariable Long id) {
        log.debug("REST request to get ReportComment : {}", id);
        Optional<ReportCommentDTO> reportCommentDTO = reportCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportCommentDTO);
    }

    /**
     * {@code DELETE  /report-comments/:id} : delete the "id" reportComment.
     *
     * @param id the id of the reportCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-comments/{id}")
    public ResponseEntity<Void> deleteReportComment(@PathVariable Long id) {
        log.debug("REST request to delete ReportComment : {}", id);
        reportCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
