package com.intuite.shopped.service;

import com.intuite.shopped.domain.ReportComment;
import com.intuite.shopped.repository.ReportCommentRepository;
import com.intuite.shopped.service.dto.ReportCommentDTO;
import com.intuite.shopped.service.mapper.ReportCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReportComment}.
 */
@Service
@Transactional
public class ReportCommentService {

    private final Logger log = LoggerFactory.getLogger(ReportCommentService.class);

    private final ReportCommentRepository reportCommentRepository;

    private final ReportCommentMapper reportCommentMapper;

    public ReportCommentService(ReportCommentRepository reportCommentRepository, ReportCommentMapper reportCommentMapper) {
        this.reportCommentRepository = reportCommentRepository;
        this.reportCommentMapper = reportCommentMapper;
    }

    /**
     * Save a reportComment.
     *
     * @param reportCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportCommentDTO save(ReportCommentDTO reportCommentDTO) {
        log.debug("Request to save ReportComment : {}", reportCommentDTO);
        ReportComment reportComment = reportCommentMapper.toEntity(reportCommentDTO);
        reportComment = reportCommentRepository.save(reportComment);
        return reportCommentMapper.toDto(reportComment);
    }

    /**
     * Get all the reportComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportComments");
        return reportCommentRepository.findAll(pageable)
            .map(reportCommentMapper::toDto);
    }


    /**
     * Get one reportComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportCommentDTO> findOne(Long id) {
        log.debug("Request to get ReportComment : {}", id);
        return reportCommentRepository.findById(id)
            .map(reportCommentMapper::toDto);
    }

    /**
     * Delete the reportComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReportComment : {}", id);
        reportCommentRepository.deleteById(id);
    }
}
