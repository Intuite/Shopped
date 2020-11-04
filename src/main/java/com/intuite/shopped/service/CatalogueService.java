package com.intuite.shopped.service;

import com.intuite.shopped.domain.Catalogue;
import com.intuite.shopped.repository.CatalogueRepository;
import com.intuite.shopped.service.dto.CatalogueDTO;
import com.intuite.shopped.service.mapper.CatalogueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Catalogue}.
 */
@Service
@Transactional
public class CatalogueService {

    private final Logger log = LoggerFactory.getLogger(CatalogueService.class);

    private final CatalogueRepository catalogueRepository;

    private final CatalogueMapper catalogueMapper;

    public CatalogueService(CatalogueRepository catalogueRepository, CatalogueMapper catalogueMapper) {
        this.catalogueRepository = catalogueRepository;
        this.catalogueMapper = catalogueMapper;
    }

    /**
     * Save a catalogue.
     *
     * @param catalogueDTO the entity to save.
     * @return the persisted entity.
     */
    public CatalogueDTO save(CatalogueDTO catalogueDTO) {
        log.debug("Request to save Catalogue : {}", catalogueDTO);
        Catalogue catalogue = catalogueMapper.toEntity(catalogueDTO);
        catalogue = catalogueRepository.save(catalogue);
        return catalogueMapper.toDto(catalogue);
    }

    /**
     * Get all the catalogues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CatalogueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Catalogues");
        return catalogueRepository.findAll(pageable)
            .map(catalogueMapper::toDto);
    }


    /**
     * Get one catalogue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CatalogueDTO> findOne(Long id) {
        log.debug("Request to get Catalogue : {}", id);
        return catalogueRepository.findById(id)
            .map(catalogueMapper::toDto);
    }

    /**
     * Delete the catalogue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Catalogue : {}", id);
        catalogueRepository.deleteById(id);
    }
}
