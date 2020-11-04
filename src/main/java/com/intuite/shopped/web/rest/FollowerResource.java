package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.FollowerService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.FollowerDTO;
import com.intuite.shopped.service.dto.FollowerCriteria;
import com.intuite.shopped.service.FollowerQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.Follower}.
 */
@RestController
@RequestMapping("/api")
public class FollowerResource {

    private final Logger log = LoggerFactory.getLogger(FollowerResource.class);

    private static final String ENTITY_NAME = "follower";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowerService followerService;

    private final FollowerQueryService followerQueryService;

    public FollowerResource(FollowerService followerService, FollowerQueryService followerQueryService) {
        this.followerService = followerService;
        this.followerQueryService = followerQueryService;
    }

    /**
     * {@code POST  /followers} : Create a new follower.
     *
     * @param followerDTO the followerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new followerDTO, or with status {@code 400 (Bad Request)} if the follower has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/followers")
    public ResponseEntity<FollowerDTO> createFollower(@Valid @RequestBody FollowerDTO followerDTO) throws URISyntaxException {
        log.debug("REST request to save Follower : {}", followerDTO);
        if (followerDTO.getId() != null) {
            throw new BadRequestAlertException("A new follower cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FollowerDTO result = followerService.save(followerDTO);
        return ResponseEntity.created(new URI("/api/followers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /followers} : Updates an existing follower.
     *
     * @param followerDTO the followerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated followerDTO,
     * or with status {@code 400 (Bad Request)} if the followerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the followerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/followers")
    public ResponseEntity<FollowerDTO> updateFollower(@Valid @RequestBody FollowerDTO followerDTO) throws URISyntaxException {
        log.debug("REST request to update Follower : {}", followerDTO);
        if (followerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FollowerDTO result = followerService.save(followerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, followerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /followers} : get all the followers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of followers in body.
     */
    @GetMapping("/followers")
    public ResponseEntity<List<FollowerDTO>> getAllFollowers(FollowerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Followers by criteria: {}", criteria);
        Page<FollowerDTO> page = followerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /followers/count} : count all the followers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/followers/count")
    public ResponseEntity<Long> countFollowers(FollowerCriteria criteria) {
        log.debug("REST request to count Followers by criteria: {}", criteria);
        return ResponseEntity.ok().body(followerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /followers/:id} : get the "id" follower.
     *
     * @param id the id of the followerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the followerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/followers/{id}")
    public ResponseEntity<FollowerDTO> getFollower(@PathVariable Long id) {
        log.debug("REST request to get Follower : {}", id);
        Optional<FollowerDTO> followerDTO = followerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(followerDTO);
    }

    /**
     * {@code DELETE  /followers/:id} : delete the "id" follower.
     *
     * @param id the id of the followerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/followers/{id}")
    public ResponseEntity<Void> deleteFollower(@PathVariable Long id) {
        log.debug("REST request to delete Follower : {}", id);
        followerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
