package com.intuite.shopped.service;

import com.intuite.shopped.domain.Follower;
import com.intuite.shopped.repository.FollowerRepository;
import com.intuite.shopped.service.dto.FollowerDTO;
import com.intuite.shopped.service.mapper.FollowerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Follower}.
 */
@Service
@Transactional
public class FollowerService {

    private final Logger log = LoggerFactory.getLogger(FollowerService.class);

    private final FollowerRepository followerRepository;

    private final FollowerMapper followerMapper;

    public FollowerService(FollowerRepository followerRepository, FollowerMapper followerMapper) {
        this.followerRepository = followerRepository;
        this.followerMapper = followerMapper;
    }

    /**
     * Save a follower.
     *
     * @param followerDTO the entity to save.
     * @return the persisted entity.
     */
    public FollowerDTO save(FollowerDTO followerDTO) {
        log.debug("Request to save Follower : {}", followerDTO);
        Follower follower = followerMapper.toEntity(followerDTO);
        follower = followerRepository.save(follower);
        return followerMapper.toDto(follower);
    }

    /**
     * Get all the followers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FollowerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Followers");
        return followerRepository.findAll(pageable)
            .map(followerMapper::toDto);
    }


    /**
     * Get one follower by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FollowerDTO> findOne(Long id) {
        log.debug("Request to get Follower : {}", id);
        return followerRepository.findById(id)
            .map(followerMapper::toDto);
    }

    /**
     * Delete the follower by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Follower : {}", id);
        followerRepository.deleteById(id);
    }
}
