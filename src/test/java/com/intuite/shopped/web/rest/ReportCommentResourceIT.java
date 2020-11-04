package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.ReportComment;
import com.intuite.shopped.domain.ReportType;
import com.intuite.shopped.domain.Comment;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.ReportCommentRepository;
import com.intuite.shopped.service.ReportCommentService;
import com.intuite.shopped.service.dto.ReportCommentDTO;
import com.intuite.shopped.service.mapper.ReportCommentMapper;
import com.intuite.shopped.service.dto.ReportCommentCriteria;
import com.intuite.shopped.service.ReportCommentQueryService;

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
 * Integration tests for the {@link ReportCommentResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportCommentResourceIT {

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private ReportCommentRepository reportCommentRepository;

    @Autowired
    private ReportCommentMapper reportCommentMapper;

    @Autowired
    private ReportCommentService reportCommentService;

    @Autowired
    private ReportCommentQueryService reportCommentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportCommentMockMvc;

    private ReportComment reportComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportComment createEntity(EntityManager em) {
        ReportComment reportComment = new ReportComment()
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
        reportComment.setType(reportType);
        // Add required entity
        Comment comment;
        if (TestUtil.findAll(em, Comment.class).isEmpty()) {
            comment = CommentResourceIT.createEntity(em);
            em.persist(comment);
            em.flush();
        } else {
            comment = TestUtil.findAll(em, Comment.class).get(0);
        }
        reportComment.setComment(comment);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reportComment.setUser(user);
        return reportComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportComment createUpdatedEntity(EntityManager em) {
        ReportComment reportComment = new ReportComment()
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
        reportComment.setType(reportType);
        // Add required entity
        Comment comment;
        if (TestUtil.findAll(em, Comment.class).isEmpty()) {
            comment = CommentResourceIT.createUpdatedEntity(em);
            em.persist(comment);
            em.flush();
        } else {
            comment = TestUtil.findAll(em, Comment.class).get(0);
        }
        reportComment.setComment(comment);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        reportComment.setUser(user);
        return reportComment;
    }

    @BeforeEach
    public void initTest() {
        reportComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportComment() throws Exception {
        int databaseSizeBeforeCreate = reportCommentRepository.findAll().size();
        // Create the ReportComment
        ReportCommentDTO reportCommentDTO = reportCommentMapper.toDto(reportComment);
        restReportCommentMockMvc.perform(post("/api/report-comments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportComment in the database
        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeCreate + 1);
        ReportComment testReportComment = reportCommentList.get(reportCommentList.size() - 1);
        assertThat(testReportComment.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testReportComment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createReportCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportCommentRepository.findAll().size();

        // Create the ReportComment with an existing ID
        reportComment.setId(1L);
        ReportCommentDTO reportCommentDTO = reportCommentMapper.toDto(reportComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportCommentMockMvc.perform(post("/api/report-comments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportComment in the database
        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportCommentRepository.findAll().size();
        // set the field null
        reportComment.setCreated(null);

        // Create the ReportComment, which fails.
        ReportCommentDTO reportCommentDTO = reportCommentMapper.toDto(reportComment);


        restReportCommentMockMvc.perform(post("/api/report-comments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportCommentDTO)))
            .andExpect(status().isBadRequest());

        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportComments() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList
        restReportCommentMockMvc.perform(get("/api/report-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getReportComment() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get the reportComment
        restReportCommentMockMvc.perform(get("/api/report-comments/{id}", reportComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportComment.getId().intValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getReportCommentsByIdFiltering() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        Long id = reportComment.getId();

        defaultReportCommentShouldBeFound("id.equals=" + id);
        defaultReportCommentShouldNotBeFound("id.notEquals=" + id);

        defaultReportCommentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReportCommentShouldNotBeFound("id.greaterThan=" + id);

        defaultReportCommentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReportCommentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReportCommentsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where created equals to DEFAULT_CREATED
        defaultReportCommentShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the reportCommentList where created equals to UPDATED_CREATED
        defaultReportCommentShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where created not equals to DEFAULT_CREATED
        defaultReportCommentShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the reportCommentList where created not equals to UPDATED_CREATED
        defaultReportCommentShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultReportCommentShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the reportCommentList where created equals to UPDATED_CREATED
        defaultReportCommentShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where created is not null
        defaultReportCommentShouldBeFound("created.specified=true");

        // Get all the reportCommentList where created is null
        defaultReportCommentShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportCommentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where status equals to DEFAULT_STATUS
        defaultReportCommentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reportCommentList where status equals to UPDATED_STATUS
        defaultReportCommentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where status not equals to DEFAULT_STATUS
        defaultReportCommentShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the reportCommentList where status not equals to UPDATED_STATUS
        defaultReportCommentShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReportCommentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reportCommentList where status equals to UPDATED_STATUS
        defaultReportCommentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportCommentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        // Get all the reportCommentList where status is not null
        defaultReportCommentShouldBeFound("status.specified=true");

        // Get all the reportCommentList where status is null
        defaultReportCommentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportCommentsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        ReportType type = reportComment.getType();
        reportCommentRepository.saveAndFlush(reportComment);
        Long typeId = type.getId();

        // Get all the reportCommentList where type equals to typeId
        defaultReportCommentShouldBeFound("typeId.equals=" + typeId);

        // Get all the reportCommentList where type equals to typeId + 1
        defaultReportCommentShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllReportCommentsByCommentIsEqualToSomething() throws Exception {
        // Get already existing entity
        Comment comment = reportComment.getComment();
        reportCommentRepository.saveAndFlush(reportComment);
        Long commentId = comment.getId();

        // Get all the reportCommentList where comment equals to commentId
        defaultReportCommentShouldBeFound("commentId.equals=" + commentId);

        // Get all the reportCommentList where comment equals to commentId + 1
        defaultReportCommentShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }


    @Test
    @Transactional
    public void getAllReportCommentsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = reportComment.getUser();
        reportCommentRepository.saveAndFlush(reportComment);
        Long userId = user.getId();

        // Get all the reportCommentList where user equals to userId
        defaultReportCommentShouldBeFound("userId.equals=" + userId);

        // Get all the reportCommentList where user equals to userId + 1
        defaultReportCommentShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportCommentShouldBeFound(String filter) throws Exception {
        restReportCommentMockMvc.perform(get("/api/report-comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restReportCommentMockMvc.perform(get("/api/report-comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportCommentShouldNotBeFound(String filter) throws Exception {
        restReportCommentMockMvc.perform(get("/api/report-comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportCommentMockMvc.perform(get("/api/report-comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReportComment() throws Exception {
        // Get the reportComment
        restReportCommentMockMvc.perform(get("/api/report-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportComment() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        int databaseSizeBeforeUpdate = reportCommentRepository.findAll().size();

        // Update the reportComment
        ReportComment updatedReportComment = reportCommentRepository.findById(reportComment.getId()).get();
        // Disconnect from session so that the updates on updatedReportComment are not directly saved in db
        em.detach(updatedReportComment);
        updatedReportComment
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        ReportCommentDTO reportCommentDTO = reportCommentMapper.toDto(updatedReportComment);

        restReportCommentMockMvc.perform(put("/api/report-comments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportCommentDTO)))
            .andExpect(status().isOk());

        // Validate the ReportComment in the database
        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeUpdate);
        ReportComment testReportComment = reportCommentList.get(reportCommentList.size() - 1);
        assertThat(testReportComment.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testReportComment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingReportComment() throws Exception {
        int databaseSizeBeforeUpdate = reportCommentRepository.findAll().size();

        // Create the ReportComment
        ReportCommentDTO reportCommentDTO = reportCommentMapper.toDto(reportComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportCommentMockMvc.perform(put("/api/report-comments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportComment in the database
        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportComment() throws Exception {
        // Initialize the database
        reportCommentRepository.saveAndFlush(reportComment);

        int databaseSizeBeforeDelete = reportCommentRepository.findAll().size();

        // Delete the reportComment
        restReportCommentMockMvc.perform(delete("/api/report-comments/{id}", reportComment.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportComment> reportCommentList = reportCommentRepository.findAll();
        assertThat(reportCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
