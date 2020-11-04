package com.intuite.shopped.service;

import com.intuite.shopped.domain.ReportPost;
import com.intuite.shopped.repository.ReportPostRepository;
import com.intuite.shopped.service.dto.ReportPostDTO;
import com.intuite.shopped.service.mapper.ReportPostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReportPost}.
 */
@Service
@Transactional
public class ReportPostService {

    private final Logger log = LoggerFactory.getLogger(ReportPostService.class);

    private final ReportPostRepository reportPostRepository;

    private final ReportPostMapper reportPostMapper;

    public ReportPostService(ReportPostRepository reportPostRepository, ReportPostMapper reportPostMapper) {
        this.reportPostRepository = reportPostRepository;
        this.reportPostMapper = reportPostMapper;
    }

    /**
     * Save a reportPost.
     *
     * @param reportPostDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportPostDTO save(ReportPostDTO reportPostDTO) {
        log.debug("Request to save ReportPost : {}", reportPostDTO);
        ReportPost reportPost = reportPostMapper.toEntity(reportPostDTO);
        reportPost = reportPostRepository.save(reportPost);
        return reportPostMapper.toDto(reportPost);
    }

    /**
     * Get all the reportPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportPostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportPosts");
        return reportPostRepository.findAll(pageable)
            .map(reportPostMapper::toDto);
    }


    /**
     * Get one reportPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportPostDTO> findOne(Long id) {
        log.debug("Request to get ReportPost : {}", id);
        return reportPostRepository.findById(id)
            .map(reportPostMapper::toDto);
    }

    /**
     * Delete the reportPost by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportPost : {}", id);
        reportPostRepository.deleteById(id);
    }
}
