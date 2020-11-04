package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.ReportPost;
import com.intuite.shopped.domain.ReportType;
import com.intuite.shopped.domain.Post;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.ReportPostRepository;
import com.intuite.shopped.service.ReportPostService;
import com.intuite.shopped.service.dto.ReportPostDTO;
import com.intuite.shopped.service.mapper.ReportPostMapper;
import com.intuite.shopped.service.dto.ReportPostCriteria;
import com.intuite.shopped.service.ReportPostQueryService;

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
 * Integration tests for the {@link ReportPostResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportPostResourceIT {

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private ReportPostRepository reportPostRepository;

    @Autowired
    private ReportPostMapper reportPostMapper;

    @Autowired
    private ReportPostService reportPostService;

    @Autowired
    private ReportPostQueryService reportPostQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportPostMockMvc;

    private ReportPost reportPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportPost createEntity(EntityManager em) {
        ReportPost reportPost = new ReportPost()
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS);
        // Add required entity
        ReportType reportType;
        if (TestUtil.findAll(em, ReportType.class).isEmpty()) {
            reportType = ReportTypeResourceIT.createEntity(em);
            em.persist(reportType);
            em.flush();
        } else {
            reportType = TestUtil.findAll(em, ReportType.class).get(0);
        }
        reportPost.setType(reportType);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        reportPost.setPost(post);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reportPost.setUser(user);
        return reportPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportPost createUpdatedEntity(EntityManager em) {
        ReportPost reportPost = new ReportPost()
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        // Add required entity
        ReportType reportType;
        if (TestUtil.findAll(em, ReportType.class).isEmpty()) {
            reportType = ReportTypeResourceIT.createUpdatedEntity(em);
            em.persist(reportType);
            em.flush();
        } else {
            reportType = TestUtil.findAll(em, ReportType.class).get(0);
        }
        reportPost.setType(reportType);
        // Add required entity
        Post post;
        if (TestUtil.findAll(em, Post.class).isEmpty()) {
            post = PostResourceIT.createUpdatedEntity(em);
            em.persist(post);
            em.flush();
        } else {
            post = TestUtil.findAll(em, Post.class).get(0);
        }
        reportPost.setPost(post);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reportPost.setUser(user);
        return reportPost;
    }

    @BeforeEach
    public void initTest() {
        reportPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportPost() throws Exception {
        int databaseSizeBeforeCreate = reportPostRepository.findAll().size();
        // Create the ReportPost
        ReportPostDTO reportPostDTO = reportPostMapper.toDto(reportPost);
        restReportPostMockMvc.perform(post("/api/report-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportPostDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportPost in the database
        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeCreate + 1);
        ReportPost testReportPost = reportPostList.get(reportPostList.size() - 1);
        assertThat(testReportPost.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testReportPost.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createReportPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportPostRepository.findAll().size();

        // Create the ReportPost with an existing ID
        reportPost.setId(1L);
        ReportPostDTO reportPostDTO = reportPostMapper.toDto(reportPost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportPostMockMvc.perform(post("/api/report-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportPost in the database
        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportPostRepository.findAll().size();
        // set the field null
        reportPost.setCreated(null);

        // Create the ReportPost, which fails.
        ReportPostDTO reportPostDTO = reportPostMapper.toDto(reportPost);


        restReportPostMockMvc.perform(post("/api/report-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportPostDTO)))
            .andExpect(status().isBadRequest());

        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportPosts() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList
        restReportPostMockMvc.perform(get("/api/report-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getReportPost() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get the reportPost
        restReportPostMockMvc.perform(get("/api/report-posts/{id}", reportPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportPost.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getReportPostsByIdFiltering() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        Long id = reportPost.getId();

        defaultReportPostShouldBeFound("id.equals=" + id);
        defaultReportPostShouldNotBeFound("id.notEquals=" + id);

        defaultReportPostShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReportPostShouldNotBeFound("id.greaterThan=" + id);

        defaultReportPostShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReportPostShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReportPostsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where created equals to DEFAULT_CREATED
        defaultReportPostShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the reportPostList where created equals to UPDATED_CREATED
        defaultReportPostShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportPostsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where created not equals to DEFAULT_CREATED
        defaultReportPostShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the reportPostList where created not equals to UPDATED_CREATED
        defaultReportPostShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportPostsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultReportPostShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the reportPostList where created equals to UPDATED_CREATED
        defaultReportPostShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportPostsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where created is not null
        defaultReportPostShouldBeFound("created.specified=true");

        // Get all the reportPostList where created is null
        defaultReportPostShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportPostsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where status equals to DEFAULT_STATUS
        defaultReportPostShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reportPostList where status equals to UPDATED_STATUS
        defaultReportPostShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportPostsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where status not equals to DEFAULT_STATUS
        defaultReportPostShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the reportPostList where status not equals to UPDATED_STATUS
        defaultReportPostShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportPostsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReportPostShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reportPostList where status equals to UPDATED_STATUS
        defaultReportPostShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportPostsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        // Get all the reportPostList where status is not null
        defaultReportPostShouldBeFound("status.specified=true");

        // Get all the reportPostList where status is null
        defaultReportPostShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportPostsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        ReportType type = reportPost.getType();
        reportPostRepository.saveAndFlush(reportPost);
        Long typeId = type.getId();

        // Get all the reportPostList where type equals to typeId
        defaultReportPostShouldBeFound("typeId.equals=" + typeId);

        // Get all the reportPostList where type equals to typeId + 1
        defaultReportPostShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllReportPostsByPostIsEqualToSomething() throws Exception {
        // Get already existing entity
        Post post = reportPost.getPost();
        reportPostRepository.saveAndFlush(reportPost);
        Long postId = post.getId();

        // Get all the reportPostList where post equals to postId
        defaultReportPostShouldBeFound("postId.equals=" + postId);

        // Get all the reportPostList where post equals to postId + 1
        defaultReportPostShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllReportPostsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = reportPost.getUser();
        reportPostRepository.saveAndFlush(reportPost);
        Long userId = user.getId();

        // Get all the reportPostList where user equals to userId
        defaultReportPostShouldBeFound("userId.equals=" + userId);

        // Get all the reportPostList where user equals to userId + 1
        defaultReportPostShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportPostShouldBeFound(String filter) throws Exception {
        restReportPostMockMvc.perform(get("/api/report-posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restReportPostMockMvc.perform(get("/api/report-posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportPostShouldNotBeFound(String filter) throws Exception {
        restReportPostMockMvc.perform(get("/api/report-posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportPostMockMvc.perform(get("/api/report-posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReportPost() throws Exception {
        // Get the reportPost
        restReportPostMockMvc.perform(get("/api/report-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportPost() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        int databaseSizeBeforeUpdate = reportPostRepository.findAll().size();

        // Update the reportPost
        ReportPost updatedReportPost = reportPostRepository.findById(reportPost.getId()).get();
        // Disconnect from session so that the updates on updatedReportPost are not directly saved in db
        em.detach(updatedReportPost);
        updatedReportPost
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        ReportPostDTO reportPostDTO = reportPostMapper.toDto(updatedReportPost);

        restReportPostMockMvc.perform(put("/api/report-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportPostDTO)))
            .andExpect(status().isOk());

        // Validate the ReportPost in the database
        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeUpdate);
        ReportPost testReportPost = reportPostList.get(reportPostList.size() - 1);
        assertThat(testReportPost.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testReportPost.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingReportPost() throws Exception {
        int databaseSizeBeforeUpdate = reportPostRepository.findAll().size();

        // Create the ReportPost
        ReportPostDTO reportPostDTO = reportPostMapper.toDto(reportPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportPostMockMvc.perform(put("/api/report-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportPost in the database
        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportPost() throws Exception {
        // Initialize the database
        reportPostRepository.saveAndFlush(reportPost);

        int databaseSizeBeforeDelete = reportPostRepository.findAll().size();

        // Delete the reportPost
        restReportPostMockMvc.perform(delete("/api/report-posts/{id}", reportPost.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportPost> reportPostList = reportPostRepository.findAll();
        assertThat(reportPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
