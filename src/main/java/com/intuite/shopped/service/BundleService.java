package com.intuite.shopped.service;

import com.intuite.shopped.domain.Bundle;
import com.intuite.shopped.repository.BundleRepository;
import com.intuite.shopped.service.dto.BundleDTO;
import com.intuite.shopped.service.mapper.BundleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Bundle}.
 */
@Service
@Transactional
public class BundleService {

    private final Logger log = LoggerFactory.getLogger(BundleService.class);

    private final BundleRepository bundleRepository;

    private final BundleMapper bundleMapper;

    public BundleService(BundleRepository bundleRepository, BundleMapper bundleMapper) {
        this.bundleRepository = bundleRepository;
        this.bundleMapper = bundleMapper;
    }

    /**
     * Save a bundle.
     *
     * @param bundleDTO the entity to save.
     * @return the persisted entity.
     */
    public BundleDTO save(BundleDTO bundleDTO) {
        log.debug("Request to save Bundle : {}", bundleDTO);
        Bundle bundle = bundleMapper.toEntity(bundleDTO);
        bundle = bundleRepository.save(bundle);
        return bundleMapper.toDto(bundle);
    }

    /**
     * Get all the bundles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BundleDTO> findAll() {
        log.debug("Request to get all Bundles");
        return bundleRepository.findAll().stream()
            .map(bundleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bundle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BundleDTO> findOne(Long id) {
        log.debug("Request to get Bundle : {}", id);
        return bundleRepository.findById(id)
            .map(bundleMapper::toDto);
    }

    /**
     * Delete the bundle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bundle : {}", id);
        bundleRepository.deleteById(id);
    }
}
