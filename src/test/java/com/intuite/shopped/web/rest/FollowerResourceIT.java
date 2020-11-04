package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Follower;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.FollowerRepository;
import com.intuite.shopped.service.FollowerService;
import com.intuite.shopped.service.dto.FollowerDTO;
import com.intuite.shopped.service.mapper.FollowerMapper;
import com.intuite.shopped.service.dto.FollowerCriteria;
import com.intuite.shopped.service.FollowerQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intuite.shopped.domain.enumeration.Status;
/**
 * Integration tests for the {@link FollowerResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FollowerResourceIT {

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private FollowerMapper followerMapper;

    @Autowired
    private FollowerService followerService;

    @Autowired
    private FollowerQueryService followerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFollowerMockMvc;

    private Follower follower;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follower createEntity(EntityManager em) {
        Follower follower = new Follower()
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        follower.setUserFollowed(user);
        // Add required entity
        follower.setUser(user);
        return follower;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follower createUpdatedEntity(EntityManager em) {
        Follower follower = new Follower()
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        follower.setUserFollowed(user);
        // Add required entity
        follower.setUser(user);
        return follower;
    }

    @BeforeEach
    public void initTest() {
        follower = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollower() throws Exception {
        int databaseSizeBeforeCreate = followerRepository.findAll().size();
        // Create the Follower
        FollowerDTO followerDTO = followerMapper.toDto(follower);
        restFollowerMockMvc.perform(post("/api/followers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerDTO)))
            .andExpect(status().isCreated());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeCreate + 1);
        Follower testFollower = followerList.get(followerList.size() - 1);
        assertThat(testFollower.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testFollower.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFollowerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followerRepository.findAll().size();

        // Create the Follower with an existing ID
        follower.setId(1L);
        FollowerDTO followerDTO = followerMapper.toDto(follower);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowerMockMvc.perform(post("/api/followers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = followerRepository.findAll().size();
        // set the field null
        follower.setCreated(null);

        // Create the Follower, which fails.
        FollowerDTO followerDTO = followerMapper.toDto(follower);


        restFollowerMockMvc.perform(post("/api/followers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerDTO)))
            .andExpect(status().isBadRequest());

        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFollowers() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList
        restFollowerMockMvc.perform(get("/api/followers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follower.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFollower() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get the follower
        restFollowerMockMvc.perform(get("/api/followers/{id}", follower.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(follower.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getFollowersByIdFiltering() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        Long id = follower.getId();

        defaultFollowerShouldBeFound("id.equals=" + id);
        defaultFollowerShouldNotBeFound("id.notEquals=" + id);

        defaultFollowerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFollowerShouldNotBeFound("id.greaterThan=" + id);

        defaultFollowerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFollowerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFollowersByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where created equals to DEFAULT_CREATED
        defaultFollowerShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the followerList where created equals to UPDATED_CREATED
        defaultFollowerShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFollowersByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where created not equals to DEFAULT_CREATED
        defaultFollowerShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the followerList where created not equals to UPDATED_CREATED
        defaultFollowerShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFollowersByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultFollowerShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the followerList where created equals to UPDATED_CREATED
        defaultFollowerShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllFollowersByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where created is not null
        defaultFollowerShouldBeFound("created.specified=true");

        // Get all the followerList where created is null
        defaultFollowerShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllFollowersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where status equals to DEFAULT_STATUS
        defaultFollowerShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the followerList where status equals to UPDATED_STATUS
        defaultFollowerShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFollowersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where status not equals to DEFAULT_STATUS
        defaultFollowerShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the followerList where status not equals to UPDATED_STATUS
        defaultFollowerShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFollowersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFollowerShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the followerList where status equals to UPDATED_STATUS
        defaultFollowerShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFollowersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        // Get all the followerList where status is not null
        defaultFollowerShouldBeFound("status.specified=true");

        // Get all the followerList where status is null
        defaultFollowerShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllFollowersByUserFollowedIsEqualToSomething() throws Exception {
        // Get already existing entity
        User userFollowed = follower.getUserFollowed();
        followerRepository.saveAndFlush(follower);
        Long userFollowedId = userFollowed.getId();

        // Get all the followerList where userFollowed equals to userFollowedId
        defaultFollowerShouldBeFound("userFollowedId.equals=" + userFollowedId);

        // Get all the followerList where userFollowed equals to userFollowedId + 1
        defaultFollowerShouldNotBeFound("userFollowedId.equals=" + (userFollowedId + 1));
    }


    @Test
    @Transactional
    public void getAllFollowersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = follower.getUser();
        followerRepository.saveAndFlush(follower);
        Long userId = user.getId();

        // Get all the followerList where user equals to userId
        defaultFollowerShouldBeFound("userId.equals=" + userId);

        // Get all the followerList where user equals to userId + 1
        defaultFollowerShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFollowerShouldBeFound(String filter) throws Exception {
        restFollowerMockMvc.perform(get("/api/followers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follower.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restFollowerMockMvc.perform(get("/api/followers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFollowerShouldNotBeFound(String filter) throws Exception {
        restFollowerMockMvc.perform(get("/api/followers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFollowerMockMvc.perform(get("/api/followers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFollower() throws Exception {
        // Get the follower
        restFollowerMockMvc.perform(get("/api/followers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollower() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        int databaseSizeBeforeUpdate = followerRepository.findAll().size();

        // Update the follower
        Follower updatedFollower = followerRepository.findById(follower.getId()).get();
        // Disconnect from session so that the updates on updatedFollower are not directly saved in db
        em.detach(updatedFollower);
        updatedFollower
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        FollowerDTO followerDTO = followerMapper.toDto(updatedFollower);

        restFollowerMockMvc.perform(put("/api/followers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerDTO)))
            .andExpect(status().isOk());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeUpdate);
        Follower testFollower = followerList.get(followerList.size() - 1);
        assertThat(testFollower.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testFollower.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFollower() throws Exception {
        int databaseSizeBeforeUpdate = followerRepository.findAll().size();

        // Create the Follower
        FollowerDTO followerDTO = followerMapper.toDto(follower);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowerMockMvc.perform(put("/api/followers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follower in the database
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFollower() throws Exception {
        // Initialize the database
        followerRepository.saveAndFlush(follower);

        int databaseSizeBeforeDelete = followerRepository.findAll().size();

        // Delete the follower
        restFollowerMockMvc.perform(delete("/api/followers/{id}", follower.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Follower> followerList = followerRepository.findAll();
        assertThat(followerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
