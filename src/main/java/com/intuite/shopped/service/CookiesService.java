package com.intuite.shopped.service;

import com.intuite.shopped.domain.Cookies;
import com.intuite.shopped.repository.CookiesRepository;
import com.intuite.shopped.service.dto.CookiesDTO;
import com.intuite.shopped.service.mapper.CookiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cookies}.
 */
@Service
@Transactional
public class CookiesService {

    private final Logger log = LoggerFactory.getLogger(CookiesService.class);

    private final CookiesRepository cookiesRepository;

    private final CookiesMapper cookiesMapper;

    public CookiesService(CookiesRepository cookiesRepository, CookiesMapper cookiesMapper) {
        this.cookiesRepository = cookiesRepository;
        this.cookiesMapper = cookiesMapper;
    }

    /**
     * Save a cookies.
     *
     * @param cookiesDTO the entity to save.
     * @return the persisted entity.
     */
    public CookiesDTO save(CookiesDTO cookiesDTO) {
        log.debug("Request to save Cookies : {}", cookiesDTO);
        Cookies cookies = cookiesMapper.toEntity(cookiesDTO);
        cookies = cookiesRepository.save(cookies);
        return cookiesMapper.toDto(cookies);
    }

    /**
     * Get all the cookies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CookiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cookies");
        return cookiesRepository.findAll(pageable)
            .map(cookiesMapper::toDto);
    }


    /**
     * Get one cookies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CookiesDTO> findOne(Long id) {
        log.debug("Request to get Cookies : {}", id);
        return cookiesRepository.findById(id)
            .map(cookiesMapper::toDto);
    }

    /**
     * Delete the cookies by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cookies : {}", id);
        cookiesRepository.deleteById(id);
    }
}
