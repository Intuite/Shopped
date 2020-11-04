package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.ReportType;
import com.intuite.shopped.repository.ReportTypeRepository;
import com.intuite.shopped.service.ReportTypeService;
import com.intuite.shopped.service.dto.ReportTypeDTO;
import com.intuite.shopped.service.mapper.ReportTypeMapper;
import com.intuite.shopped.service.dto.ReportTypeCriteria;
import com.intuite.shopped.service.ReportTypeQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intuite.shopped.domain.enumeration.Status;
/**
 * Integration tests for the {@link ReportTypeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReportTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private ReportTypeMapper reportTypeMapper;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportTypeQueryService reportTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportTypeMockMvc;

    private ReportType reportType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportType createEntity(EntityManager em) {
        ReportType reportType = new ReportType()
            .name(DEFAULT_NAME)
            .text(DEFAULT_TEXT)
            .status(DEFAULT_STATUS);
        return reportType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportType createUpdatedEntity(EntityManager em) {
        ReportType reportType = new ReportType()
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT)
            .status(UPDATED_STATUS);
        return reportType;
    }

    @BeforeEach
    public void initTest() {
        reportType = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportType() throws Exception {
        int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();
        // Create the ReportType
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);
        restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
        assertThat(testReportType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReportType.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testReportType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createReportTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();

        // Create the ReportType with an existing ID
        reportType.setId(1L);
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportTypeRepository.findAll().size();
        // set the field null
        reportType.setName(null);

        // Create the ReportType, which fails.
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);


        restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportTypeRepository.findAll().size();
        // set the field null
        reportType.setText(null);

        // Create the ReportType, which fails.
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);


        restReportTypeMockMvc.perform(post("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportTypes() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get the reportType
        restReportTypeMockMvc.perform(get("/api/report-types/{id}", reportType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getReportTypesByIdFiltering() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        Long id = reportType.getId();

        defaultReportTypeShouldBeFound("id.equals=" + id);
        defaultReportTypeShouldNotBeFound("id.notEquals=" + id);

        defaultReportTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReportTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultReportTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReportTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReportTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name equals to DEFAULT_NAME
        defaultReportTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reportTypeList where name equals to UPDATED_NAME
        defaultReportTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name not equals to DEFAULT_NAME
        defaultReportTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the reportTypeList where name not equals to UPDATED_NAME
        defaultReportTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReportTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reportTypeList where name equals to UPDATED_NAME
        defaultReportTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name is not null
        defaultReportTypeShouldBeFound("name.specified=true");

        // Get all the reportTypeList where name is null
        defaultReportTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReportTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name contains DEFAULT_NAME
        defaultReportTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the reportTypeList where name contains UPDATED_NAME
        defaultReportTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where name does not contain DEFAULT_NAME
        defaultReportTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the reportTypeList where name does not contain UPDATED_NAME
        defaultReportTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReportTypesByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text equals to DEFAULT_TEXT
        defaultReportTypeShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the reportTypeList where text equals to UPDATED_TEXT
        defaultReportTypeShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllReportTypesByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text not equals to DEFAULT_TEXT
        defaultReportTypeShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the reportTypeList where text not equals to UPDATED_TEXT
        defaultReportTypeShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllReportTypesByTextIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultReportTypeShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the reportTypeList where text equals to UPDATED_TEXT
        defaultReportTypeShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllReportTypesByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text is not null
        defaultReportTypeShouldBeFound("text.specified=true");

        // Get all the reportTypeList where text is null
        defaultReportTypeShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllReportTypesByTextContainsSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text contains DEFAULT_TEXT
        defaultReportTypeShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the reportTypeList where text contains UPDATED_TEXT
        defaultReportTypeShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllReportTypesByTextNotContainsSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where text does not contain DEFAULT_TEXT
        defaultReportTypeShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the reportTypeList where text does not contain UPDATED_TEXT
        defaultReportTypeShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }


    @Test
    @Transactional
    public void getAllReportTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where status equals to DEFAULT_STATUS
        defaultReportTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the reportTypeList where status equals to UPDATED_STATUS
        defaultReportTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where status not equals to DEFAULT_STATUS
        defaultReportTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the reportTypeList where status not equals to UPDATED_STATUS
        defaultReportTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultReportTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the reportTypeList where status equals to UPDATED_STATUS
        defaultReportTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllReportTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where status is not null
        defaultReportTypeShouldBeFound("status.specified=true");

        // Get all the reportTypeList where status is null
        defaultReportTypeShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportTypeShouldBeFound(String filter) throws Exception {
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restReportTypeMockMvc.perform(get("/api/report-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportTypeShouldNotBeFound(String filter) throws Exception {
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportTypeMockMvc.perform(get("/api/report-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingReportType() throws Exception {
        // Get the reportType
        restReportTypeMockMvc.perform(get("/api/report-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

        // Update the reportType
        ReportType updatedReportType = reportTypeRepository.findById(reportType.getId()).get();
        // Disconnect from session so that the updates on updatedReportType are not directly saved in db
        em.detach(updatedReportType);
        updatedReportType
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT)
            .status(UPDATED_STATUS);
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(updatedReportType);

        restReportTypeMockMvc.perform(put("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);
        ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
        assertThat(testReportType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportType.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testReportType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingReportType() throws Exception {
        int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

        // Create the ReportType
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportTypeMockMvc.perform(put("/api/report-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        int databaseSizeBeforeDelete = reportTypeRepository.findAll().size();

        // Delete the reportType
        restReportTypeMockMvc.perform(delete("/api/report-types/{id}", reportType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
