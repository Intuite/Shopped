package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Commendation;
import com.intuite.shopped.domain.Post;
import com.intuite.shopped.domain.Award;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.CommendationRepository;
import com.intuite.shopped.service.CommendationService;
import com.intuite.shopped.service.dto.CommendationDTO;
import com.intuite.shopped.service.mapper.CommendationMapper;
import com.intuite.shopped.service.dto.CommendationCriteria;
import com.intuite.shopped.service.CommendationQueryService;

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

/**
 * Integration tests for the {@link CommendationResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommendationResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommendationRepository commendationRepository;

    @Autowired
    private CommendationMapper commendationMapper;

    @Autowired
    private CommendationService commendationService;

    @Autowired
    private CommendationQueryService commendationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommendationMockMvc;

    private Commendation commendation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commendation createEntity(EntityManager em) {
        Commendation commendation = new Commendation()
            .date(DEFAULT_DATE);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        commendation.setPost(post);
        // Add required entity
        Award award;
        if (TestUtil.findAll(em, Award.class).isEmpty()) {
            award = AwardResourceIT.createEntity(em);
            em.persist(award);
            em.flush();
        } else {
            award = TestUtil.findAll(em, Award.class).get(0);
        }
        commendation.setAward(award);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        commendation.setUser(user);
        return commendation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commendation createUpdatedEntity(EntityManager em) {
        Commendation commendation = new Commendation()
            .date(UPDATED_DATE);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createUpdatedEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        commendation.setPost(post);
        // Add required entity
        Award award;
        if (TestUtil.findAll(em, Award.class).isEmpty()) {
            award = AwardResourceIT.createUpdatedEntity(em);
            em.persist(award);
            em.flush();
        } else {
            award = TestUtil.findAll(em, Award.class).get(0);
        }
        commendation.setAward(award);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        commendation.setUser(user);
        return commendation;
    }

    @BeforeEach
    public void initTest() {
        commendation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommendation() throws Exception {
        int databaseSizeBeforeCreate = commendationRepository.findAll().size();
        // Create the Commendation
        CommendationDTO commendationDTO = commendationMapper.toDto(commendation);
        restCommendationMockMvc.perform(post("/api/commendations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commendationDTO)))
            .andExpect(status().isCreated());

        // Validate the Commendation in the database
        List<Commendation> commendationList = commendationRepository.findAll();
        assertThat(commendationList).hasSize(databaseSizeBeforeCreate + 1);
        Commendation testCommendation = commendationList.get(commendationList.size() - 1);
        assertThat(testCommendation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createCommendationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commendationRepository.findAll().size();

        // Create the Commendation with an existing ID
        commendation.setId(1L);
        CommendationDTO commendationDTO = commendationMapper.toDto(commendation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommendationMockMvc.perform(post("/api/commendations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commendationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commendation in the database
        List<Commendation> commendationList = commendationRepository.findAll();
        assertThat(commendationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommendations() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get all the commendationList
        restCommendationMockMvc.perform(get("/api/commendations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commendation.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommendation() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get the commendation
        restCommendationMockMvc.perform(get("/api/commendations/{id}", commendation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commendation.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }


    @Test
    @Transactional
    public void getCommendationsByIdFiltering() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        Long id = commendation.getId();

        defaultCommendationShouldBeFound("id.equals=" + id);
        defaultCommendationShouldNotBeFound("id.notEquals=" + id);

        defaultCommendationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommendationShouldNotBeFound("id.greaterThan=" + id);

        defaultCommendationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommendationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommendationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get all the commendationList where date equals to DEFAULT_DATE
        defaultCommendationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the commendationList where date equals to UPDATED_DATE
        defaultCommendationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommendationsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get all the commendationList where date not equals to DEFAULT_DATE
        defaultCommendationShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the commendationList where date not equals to UPDATED_DATE
        defaultCommendationShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommendationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get all the commendationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCommendationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the commendationList where date equals to UPDATED_DATE
        defaultCommendationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCommendationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        // Get all the commendationList where date is not null
        defaultCommendationShouldBeFound("date.specified=true");

        // Get all the commendationList where date is null
        defaultCommendationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommendationsByPostIsEqualToSomething() throws Exception {
        // Get already existing entity
        Post post = commendation.getPost();
        commendationRepository.saveAndFlush(commendation);
        Long postId = post.getId();

        // Get all the commendationList where post equals to postId
        defaultCommendationShouldBeFound("postId.equals=" + postId);

        // Get all the commendationList where post equals to postId + 1
        defaultCommendationShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllCommendationsByAwardIsEqualToSomething() throws Exception {
        // Get already existing entity
        Award award = commendation.getAward();
        commendationRepository.saveAndFlush(commendation);
        Long awardId = award.getId();

        // Get all the commendationList where award equals to awardId
        defaultCommendationShouldBeFound("awardId.equals=" + awardId);

        // Get all the commendationList where award equals to awardId + 1
        defaultCommendationShouldNotBeFound("awardId.equals=" + (awardId + 1));
    }


    @Test
    @Transactional
    public void getAllCommendationsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = commendation.getUser();
        commendationRepository.saveAndFlush(commendation);
        Long userId = user.getId();

        // Get all the commendationList where user equals to userId
        defaultCommendationShouldBeFound("userId.equals=" + userId);

        // Get all the commendationList where user equals to userId + 1
        defaultCommendationShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommendationShouldBeFound(String filter) throws Exception {
        restCommendationMockMvc.perform(get("/api/commendations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commendation.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restCommendationMockMvc.perform(get("/api/commendations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommendationShouldNotBeFound(String filter) throws Exception {
        restCommendationMockMvc.perform(get("/api/commendations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommendationMockMvc.perform(get("/api/commendations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommendation() throws Exception {
        // Get the commendation
        restCommendationMockMvc.perform(get("/api/commendations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommendation() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        int databaseSizeBeforeUpdate = commendationRepository.findAll().size();

        // Update the commendation
        Commendation updatedCommendation = commendationRepository.findById(commendation.getId()).get();
        // Disconnect from session so that the updates on updatedCommendation are not directly saved in db
        em.detach(updatedCommendation);
        updatedCommendation
            .date(UPDATED_DATE);
        CommendationDTO commendationDTO = commendationMapper.toDto(updatedCommendation);

        restCommendationMockMvc.perform(put("/api/commendations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commendationDTO)))
            .andExpect(status().isOk());

        // Validate the Commendation in the database
        List<Commendation> commendationList = commendationRepository.findAll();
        assertThat(commendationList).hasSize(databaseSizeBeforeUpdate);
        Commendation testCommendation = commendationList.get(commendationList.size() - 1);
        assertThat(testCommendation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommendation() throws Exception {
        int databaseSizeBeforeUpdate = commendationRepository.findAll().size();

        // Create the Commendation
        CommendationDTO commendationDTO = commendationMapper.toDto(commendation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommendationMockMvc.perform(put("/api/commendations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commendationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commendation in the database
        List<Commendation> commendationList = commendationRepository.findAll();
        assertThat(commendationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommendation() throws Exception {
        // Initialize the database
        commendationRepository.saveAndFlush(commendation);

        int databaseSizeBeforeDelete = commendationRepository.findAll().size();

        // Delete the commendation
        restCommendationMockMvc.perform(delete("/api/commendations/{id}", commendation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commendation> commendationList = commendationRepository.findAll();
        assertThat(commendationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
