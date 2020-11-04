package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Bite;
import com.intuite.shopped.domain.Post;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.BiteRepository;
import com.intuite.shopped.service.BiteService;
import com.intuite.shopped.service.dto.BiteDTO;
import com.intuite.shopped.service.mapper.BiteMapper;
import com.intuite.shopped.service.dto.BiteCriteria;
import com.intuite.shopped.service.BiteQueryService;

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
 * Integration tests for the {@link BiteResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BiteResourceIT {

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private BiteRepository biteRepository;

    @Autowired
    private BiteMapper biteMapper;

    @Autowired
    private BiteService biteService;

    @Autowired
    private BiteQueryService biteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBiteMockMvc;

    private Bite bite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bite createEntity(EntityManager em) {
        Bite bite = new Bite()
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        bite.setPost(post);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        bite.setUser(user);
        return bite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bite createUpdatedEntity(EntityManager em) {
        Bite bite = new Bite()
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createUpdatedEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        bite.setPost(post);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        bite.setUser(user);
        return bite;
    }

    @BeforeEach
    public void initTest() {
        bite = createEntity(em);
    }

    @Test
    @Transactional
    public void createBite() throws Exception {
        int databaseSizeBeforeCreate = biteRepository.findAll().size();
        // Create the Bite
        BiteDTO biteDTO = biteMapper.toDto(bite);
        restBiteMockMvc.perform(post("/api/bites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biteDTO)))
            .andExpect(status().isCreated());

        // Validate the Bite in the database
        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeCreate + 1);
        Bite testBite = biteList.get(biteList.size() - 1);
        assertThat(testBite.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testBite.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = biteRepository.findAll().size();

        // Create the Bite with an existing ID
        bite.setId(1L);
        BiteDTO biteDTO = biteMapper.toDto(bite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBiteMockMvc.perform(post("/api/bites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bite in the database
        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = biteRepository.findAll().size();
        // set the field null
        bite.setCreated(null);

        // Create the Bite, which fails.
        BiteDTO biteDTO = biteMapper.toDto(bite);


        restBiteMockMvc.perform(post("/api/bites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biteDTO)))
            .andExpect(status().isBadRequest());

        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBites() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList
        restBiteMockMvc.perform(get("/api/bites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bite.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBite() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get the bite
        restBiteMockMvc.perform(get("/api/bites/{id}", bite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bite.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getBitesByIdFiltering() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        Long id = bite.getId();

        defaultBiteShouldBeFound("id.equals=" + id);
        defaultBiteShouldNotBeFound("id.notEquals=" + id);

        defaultBiteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBiteShouldNotBeFound("id.greaterThan=" + id);

        defaultBiteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBiteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBitesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where created equals to DEFAULT_CREATED
        defaultBiteShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the biteList where created equals to UPDATED_CREATED
        defaultBiteShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBitesByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where created not equals to DEFAULT_CREATED
        defaultBiteShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the biteList where created not equals to UPDATED_CREATED
        defaultBiteShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBitesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultBiteShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the biteList where created equals to UPDATED_CREATED
        defaultBiteShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllBitesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where created is not null
        defaultBiteShouldBeFound("created.specified=true");

        // Get all the biteList where created is null
        defaultBiteShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllBitesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where status equals to DEFAULT_STATUS
        defaultBiteShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the biteList where status equals to UPDATED_STATUS
        defaultBiteShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBitesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where status not equals to DEFAULT_STATUS
        defaultBiteShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the biteList where status not equals to UPDATED_STATUS
        defaultBiteShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBitesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBiteShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the biteList where status equals to UPDATED_STATUS
        defaultBiteShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBitesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        // Get all the biteList where status is not null
        defaultBiteShouldBeFound("status.specified=true");

        // Get all the biteList where status is null
        defaultBiteShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllBitesByPostIsEqualToSomething() throws Exception {
        // Get already existing entity
        Post post = bite.getPost();
        biteRepository.saveAndFlush(bite);
        Long postId = post.getId();

        // Get all the biteList where post equals to postId
        defaultBiteShouldBeFound("postId.equals=" + postId);

        // Get all the biteList where post equals to postId + 1
        defaultBiteShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllBitesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = bite.getUser();
        biteRepository.saveAndFlush(bite);
        Long userId = user.getId();

        // Get all the biteList where user equals to userId
        defaultBiteShouldBeFound("userId.equals=" + userId);

        // Get all the biteList where user equals to userId + 1
        defaultBiteShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBiteShouldBeFound(String filter) throws Exception {
        restBiteMockMvc.perform(get("/api/bites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bite.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restBiteMockMvc.perform(get("/api/bites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBiteShouldNotBeFound(String filter) throws Exception {
        restBiteMockMvc.perform(get("/api/bites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBiteMockMvc.perform(get("/api/bites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBite() throws Exception {
        // Get the bite
        restBiteMockMvc.perform(get("/api/bites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBite() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        int databaseSizeBeforeUpdate = biteRepository.findAll().size();

        // Update the bite
        Bite updatedBite = biteRepository.findById(bite.getId()).get();
        // Disconnect from session so that the updates on updatedBite are not directly saved in db
        em.detach(updatedBite);
        updatedBite
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        BiteDTO biteDTO = biteMapper.toDto(updatedBite);

        restBiteMockMvc.perform(put("/api/bites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biteDTO)))
            .andExpect(status().isOk());

        // Validate the Bite in the database
        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeUpdate);
        Bite testBite = biteList.get(biteList.size() - 1);
        assertThat(testBite.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testBite.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBite() throws Exception {
        int databaseSizeBeforeUpdate = biteRepository.findAll().size();

        // Create the Bite
        BiteDTO biteDTO = biteMapper.toDto(bite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBiteMockMvc.perform(put("/api/bites").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(biteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bite in the database
        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBite() throws Exception {
        // Initialize the database
        biteRepository.saveAndFlush(bite);

        int databaseSizeBeforeDelete = biteRepository.findAll().size();

        // Delete the bite
        restBiteMockMvc.perform(delete("/api/bites/{id}", bite.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bite> biteList = biteRepository.findAll();
        assertThat(biteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
