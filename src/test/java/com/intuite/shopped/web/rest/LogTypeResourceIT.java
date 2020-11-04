package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.LogType;
import com.intuite.shopped.repository.LogTypeRepository;
import com.intuite.shopped.service.LogTypeService;
import com.intuite.shopped.service.dto.LogTypeDTO;
import com.intuite.shopped.service.mapper.LogTypeMapper;
import com.intuite.shopped.service.dto.LogTypeCriteria;
import com.intuite.shopped.service.LogTypeQueryService;

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
 * Integration tests for the {@link LogTypeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private LogTypeRepository logTypeRepository;

    @Autowired
    private LogTypeMapper logTypeMapper;

    @Autowired
    private LogTypeService logTypeService;

    @Autowired
    private LogTypeQueryService logTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogTypeMockMvc;

    private LogType logType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogType createEntity(EntityManager em) {
        LogType logType = new LogType()
            .name(DEFAULT_NAME)
            .template(DEFAULT_TEMPLATE)
            .status(DEFAULT_STATUS);
        return logType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogType createUpdatedEntity(EntityManager em) {
        LogType logType = new LogType()
            .name(UPDATED_NAME)
            .template(UPDATED_TEMPLATE)
            .status(UPDATED_STATUS);
        return logType;
    }

    @BeforeEach
    public void initTest() {
        logType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogType() throws Exception {
        int databaseSizeBeforeCreate = logTypeRepository.findAll().size();
        // Create the LogType
        LogTypeDTO logTypeDTO = logTypeMapper.toDto(logType);
        restLogTypeMockMvc.perform(post("/api/log-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LogType in the database
        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LogType testLogType = logTypeList.get(logTypeList.size() - 1);
        assertThat(testLogType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLogType.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testLogType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createLogTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logTypeRepository.findAll().size();

        // Create the LogType with an existing ID
        logType.setId(1L);
        LogTypeDTO logTypeDTO = logTypeMapper.toDto(logType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogTypeMockMvc.perform(post("/api/log-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogType in the database
        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = logTypeRepository.findAll().size();
        // set the field null
        logType.setName(null);

        // Create the LogType, which fails.
        LogTypeDTO logTypeDTO = logTypeMapper.toDto(logType);


        restLogTypeMockMvc.perform(post("/api/log-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogTypes() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList
        restLogTypeMockMvc.perform(get("/api/log-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getLogType() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get the logType
        restLogTypeMockMvc.perform(get("/api/log-types/{id}", logType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getLogTypesByIdFiltering() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        Long id = logType.getId();

        defaultLogTypeShouldBeFound("id.equals=" + id);
        defaultLogTypeShouldNotBeFound("id.notEquals=" + id);

        defaultLogTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultLogTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name equals to DEFAULT_NAME
        defaultLogTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the logTypeList where name equals to UPDATED_NAME
        defaultLogTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name not equals to DEFAULT_NAME
        defaultLogTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the logTypeList where name not equals to UPDATED_NAME
        defaultLogTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLogTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the logTypeList where name equals to UPDATED_NAME
        defaultLogTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name is not null
        defaultLogTypeShouldBeFound("name.specified=true");

        // Get all the logTypeList where name is null
        defaultLogTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name contains DEFAULT_NAME
        defaultLogTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the logTypeList where name contains UPDATED_NAME
        defaultLogTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLogTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where name does not contain DEFAULT_NAME
        defaultLogTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the logTypeList where name does not contain UPDATED_NAME
        defaultLogTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLogTypesByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template equals to DEFAULT_TEMPLATE
        defaultLogTypeShouldBeFound("template.equals=" + DEFAULT_TEMPLATE);

        // Get all the logTypeList where template equals to UPDATED_TEMPLATE
        defaultLogTypeShouldNotBeFound("template.equals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllLogTypesByTemplateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template not equals to DEFAULT_TEMPLATE
        defaultLogTypeShouldNotBeFound("template.notEquals=" + DEFAULT_TEMPLATE);

        // Get all the logTypeList where template not equals to UPDATED_TEMPLATE
        defaultLogTypeShouldBeFound("template.notEquals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllLogTypesByTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template in DEFAULT_TEMPLATE or UPDATED_TEMPLATE
        defaultLogTypeShouldBeFound("template.in=" + DEFAULT_TEMPLATE + "," + UPDATED_TEMPLATE);

        // Get all the logTypeList where template equals to UPDATED_TEMPLATE
        defaultLogTypeShouldNotBeFound("template.in=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllLogTypesByTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template is not null
        defaultLogTypeShouldBeFound("template.specified=true");

        // Get all the logTypeList where template is null
        defaultLogTypeShouldNotBeFound("template.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogTypesByTemplateContainsSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template contains DEFAULT_TEMPLATE
        defaultLogTypeShouldBeFound("template.contains=" + DEFAULT_TEMPLATE);

        // Get all the logTypeList where template contains UPDATED_TEMPLATE
        defaultLogTypeShouldNotBeFound("template.contains=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllLogTypesByTemplateNotContainsSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where template does not contain DEFAULT_TEMPLATE
        defaultLogTypeShouldNotBeFound("template.doesNotContain=" + DEFAULT_TEMPLATE);

        // Get all the logTypeList where template does not contain UPDATED_TEMPLATE
        defaultLogTypeShouldBeFound("template.doesNotContain=" + UPDATED_TEMPLATE);
    }


    @Test
    @Transactional
    public void getAllLogTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where status equals to DEFAULT_STATUS
        defaultLogTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the logTypeList where status equals to UPDATED_STATUS
        defaultLogTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLogTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where status not equals to DEFAULT_STATUS
        defaultLogTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the logTypeList where status not equals to UPDATED_STATUS
        defaultLogTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLogTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLogTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the logTypeList where status equals to UPDATED_STATUS
        defaultLogTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLogTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        // Get all the logTypeList where status is not null
        defaultLogTypeShouldBeFound("status.specified=true");

        // Get all the logTypeList where status is null
        defaultLogTypeShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogTypeShouldBeFound(String filter) throws Exception {
        restLogTypeMockMvc.perform(get("/api/log-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restLogTypeMockMvc.perform(get("/api/log-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogTypeShouldNotBeFound(String filter) throws Exception {
        restLogTypeMockMvc.perform(get("/api/log-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogTypeMockMvc.perform(get("/api/log-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLogType() throws Exception {
        // Get the logType
        restLogTypeMockMvc.perform(get("/api/log-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogType() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        int databaseSizeBeforeUpdate = logTypeRepository.findAll().size();

        // Update the logType
        LogType updatedLogType = logTypeRepository.findById(logType.getId()).get();
        // Disconnect from session so that the updates on updatedLogType are not directly saved in db
        em.detach(updatedLogType);
        updatedLogType
            .name(UPDATED_NAME)
            .template(UPDATED_TEMPLATE)
            .status(UPDATED_STATUS);
        LogTypeDTO logTypeDTO = logTypeMapper.toDto(updatedLogType);

        restLogTypeMockMvc.perform(put("/api/log-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LogType in the database
        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeUpdate);
        LogType testLogType = logTypeList.get(logTypeList.size() - 1);
        assertThat(testLogType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLogType.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testLogType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLogType() throws Exception {
        int databaseSizeBeforeUpdate = logTypeRepository.findAll().size();

        // Create the LogType
        LogTypeDTO logTypeDTO = logTypeMapper.toDto(logType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogTypeMockMvc.perform(put("/api/log-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogType in the database
        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogType() throws Exception {
        // Initialize the database
        logTypeRepository.saveAndFlush(logType);

        int databaseSizeBeforeDelete = logTypeRepository.findAll().size();

        // Delete the logType
        restLogTypeMockMvc.perform(delete("/api/log-types/{id}", logType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogType> logTypeList = logTypeRepository.findAll();
        assertThat(logTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
