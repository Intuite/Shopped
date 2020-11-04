package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.ReportPostService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.ReportPostDTO;
import com.intuite.shopped.service.dto.ReportPostCriteria;
import com.intuite.shopped.service.ReportPostQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.ReportPost}.
 */
@RestController
@RequestMapping("/api")
public class ReportPostResource {

    private final Logger log = LoggerFactory.getLogger(ReportPostResource.class);

    private static final String ENTITY_NAME = "reportPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportPostService reportPostService;

    private final ReportPostQueryService reportPostQueryService;

    public ReportPostResource(ReportPostService reportPostService, ReportPostQueryService reportPostQueryService) {
        this.reportPostService = reportPostService;
        this.reportPostQueryService = reportPostQueryService;
    }

    /**
     * {@code POST  /report-posts} : Create a new reportPost.
     *
     * @param reportPostDTO the reportPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportPostDTO, or with status {@code 400 (Bad Request)} if the reportPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-posts")
    public ResponseEntity<ReportPostDTO> createReportPost(@Valid @RequestBody ReportPostDTO reportPostDTO) throws URISyntaxException {
        log.debug("REST request to save ReportPost : {}", reportPostDTO);
        if (reportPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportPostDTO result = reportPostService.save(reportPostDTO);
        return ResponseEntity.created(new URI("/api/report-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-posts} : Updates an existing reportPost.
     *
     * @param reportPostDTO the reportPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportPostDTO,
     * or with status {@code 400 (Bad Request)} if the reportPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-posts")
    public ResponseEntity<ReportPostDTO> updateReportPost(@Valid @RequestBody ReportPostDTO reportPostDTO) throws URISyntaxException {
        log.debug("REST request to update ReportPost : {}", reportPostDTO);
        if (reportPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportPostDTO result = reportPostService.save(reportPostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reportPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-posts} : get all the reportPosts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportPosts in body.
     */
    @GetMapping("/report-posts")
    public ResponseEntity<List<ReportPostDTO>> getAllReportPosts(ReportPostCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReportPosts by criteria: {}", criteria);
        Page<ReportPostDTO> page = reportPostQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-posts/count} : count all the reportPosts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/report-posts/count")
    public ResponseEntity<Long> countReportPosts(ReportPostCriteria criteria) {
        log.debug("REST request to count ReportPosts by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportPostQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /report-posts/:id} : get the "id" reportPost.
     *
     * @param id the id of the reportPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-posts/{id}")
    public ResponseEntity<ReportPostDTO> getReportPost(@PathVariable Long id) {
        log.debug("REST request to get ReportPost : {}", id);
        Optional<ReportPostDTO> reportPostDTO = reportPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportPostDTO);
    }

    /**
     * {@code DELETE  /report-posts/:id} : delete the "id" reportPost.
     *
     * @param id the id of the reportPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-posts/{id}")
    public ResponseEntity<Void> deleteReportPost(@PathVariable Long id) {
        log.debug("REST request to delete ReportPost : {}", id);
        reportPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
