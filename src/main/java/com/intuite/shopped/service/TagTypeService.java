package com.intuite.shopped.service;

import com.intuite.shopped.domain.TagType;
import com.intuite.shopped.repository.TagTypeRepository;
import com.intuite.shopped.service.dto.TagTypeDTO;
import com.intuite.shopped.service.mapper.TagTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagType}.
 */
@Service
@Transactional
public class TagTypeService {

    private final Logger log = LoggerFactory.getLogger(TagTypeService.class);

    private final TagTypeRepository tagTypeRepository;

    private final TagTypeMapper tagTypeMapper;

    public TagTypeService(TagTypeRepository tagTypeRepository, TagTypeMapper tagTypeMapper) {
        this.tagTypeRepository = tagTypeRepository;
        this.tagTypeMapper = tagTypeMapper;
    }

    /**
     * Save a tagType.
     *
     * @param tagTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public TagTypeDTO save(TagTypeDTO tagTypeDTO) {
        log.debug("Request to save TagType : {}", tagTypeDTO);
        TagType tagType = tagTypeMapper.toEntity(tagTypeDTO);
        tagType = tagTypeRepository.save(tagType);
        return tagTypeMapper.toDto(tagType);
    }

    /**
     * Get all the tagTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TagTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagTypes");
        return tagTypeRepository.findAll(pageable)
            .map(tagTypeMapper::toDto);
    }


    /**
     * Get one tagType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TagTypeDTO> findOne(Long id) {
        log.debug("Request to get TagType : {}", id);
        return tagTypeRepository.findById(id)
            .map(tagTypeMapper::toDto);
    }

    /**
     * Delete the tagType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TagType : {}", id);
        tagTypeRepository.deleteById(id);
    }
}
