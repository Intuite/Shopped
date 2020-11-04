package com.intuite.shopped.web.rest;

import com.intuite.shopped.service.TagTypeService;
import com.intuite.shopped.web.rest.errors.BadRequestAlertException;
import com.intuite.shopped.service.dto.TagTypeDTO;
import com.intuite.shopped.service.dto.TagTypeCriteria;
import com.intuite.shopped.service.TagTypeQueryService;

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
 * REST controller for managing {@link com.intuite.shopped.domain.TagType}.
 */
@RestController
@RequestMapping("/api")
public class TagTypeResource {

    private final Logger log = LoggerFactory.getLogger(TagTypeResource.class);

    private static final String ENTITY_NAME = "tagType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagTypeService tagTypeService;

    private final TagTypeQueryService tagTypeQueryService;

    public TagTypeResource(TagTypeService tagTypeService, TagTypeQueryService tagTypeQueryService) {
        this.tagTypeService = tagTypeService;
        this.tagTypeQueryService = tagTypeQueryService;
    }

    /**
     * {@code POST  /tag-types} : Create a new tagType.
     *
     * @param tagTypeDTO the tagTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagTypeDTO, or with status {@code 400 (Bad Request)} if the tagType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-types")
    public ResponseEntity<TagTypeDTO> createTagType(@Valid @RequestBody TagTypeDTO tagTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TagType : {}", tagTypeDTO);
        if (tagTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagTypeDTO result = tagTypeService.save(tagTypeDTO);
        return ResponseEntity.created(new URI("/api/tag-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-types} : Updates an existing tagType.
     *
     * @param tagTypeDTO the tagTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagTypeDTO,
     * or with status {@code 400 (Bad Request)} if the tagTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-types")
    public ResponseEntity<TagTypeDTO> updateTagType(@Valid @RequestBody TagTypeDTO tagTypeDTO) throws URISyntaxException {
        log.debug("REST request to update TagType : {}", tagTypeDTO);
        if (tagTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagTypeDTO result = tagTypeService.save(tagTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-types} : get all the tagTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagTypes in body.
     */
    @GetMapping("/tag-types")
    public ResponseEntity<List<TagTypeDTO>> getAllTagTypes(TagTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TagTypes by criteria: {}", criteria);
        Page<TagTypeDTO> page = tagTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-types/count} : count all the tagTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tag-types/count")
    public ResponseEntity<Long> countTagTypes(TagTypeCriteria criteria) {
        log.debug("REST request to count TagTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tagTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tag-types/:id} : get the "id" tagType.
     *
     * @param id the id of the tagTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-types/{id}")
    public ResponseEntity<TagTypeDTO> getTagType(@PathVariable Long id) {
        log.debug("REST request to get TagType : {}", id);
        Optional<TagTypeDTO> tagTypeDTO = tagTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagTypeDTO);
    }

    /**
     * {@code DELETE  /tag-types/:id} : delete the "id" tagType.
     *
     * @param id the id of the tagTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-types/{id}")
    public ResponseEntity<Void> deleteTagType(@PathVariable Long id) {
        log.debug("REST request to delete TagType : {}", id);
        tagTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
