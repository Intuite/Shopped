package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Log;
import com.intuite.shopped.domain.LogType;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.LogRepository;
import com.intuite.shopped.service.LogService;
import com.intuite.shopped.service.dto.LogDTO;
import com.intuite.shopped.service.mapper.LogMapper;
import com.intuite.shopped.service.dto.LogCriteria;
import com.intuite.shopped.service.LogQueryService;

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
 * Integration tests for the {@link LogResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LogResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private LogQueryService logQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogMockMvc;

    private Log log;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Log createEntity(EntityManager em) {
        Log log = new Log()
            .description(DEFAULT_DESCRIPTION)
            .created(DEFAULT_CREATED);
        // Add required entity
        LogType logType;
        if (TestUtil.findAll(em, LogType.class).isEmpty()) {
            logType = LogTypeResourceIT.createEntity(em);
            em.persist(logType);
            em.flush();
        } else {
            logType = TestUtil.findAll(em, LogType.class).get(0);
        }
        log.setType(logType);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        log.setUser(user);
        return log;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Log createUpdatedEntity(EntityManager em) {
        Log log = new Log()
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED);
        // Add required entity
        LogType logType;
        if (TestUtil.findAll(em, LogType.class).isEmpty()) {
            logType = LogTypeResourceIT.createUpdatedEntity(em);
            em.persist(logType);
            em.flush();
        } else {
            logType = TestUtil.findAll(em, LogType.class).get(0);
        }
        log.setType(logType);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        log.setUser(user);
        return log;
    }

    @BeforeEach
    public void initTest() {
        log = createEntity(em);
    }

    @Test
    @Transactional
    public void createLog() throws Exception {
        int databaseSizeBeforeCreate = logRepository.findAll().size();
        // Create the Log
        LogDTO logDTO = logMapper.toDto(log);
        restLogMockMvc.perform(post("/api/logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isCreated());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeCreate + 1);
        Log testLog = logList.get(logList.size() - 1);
        assertThat(testLog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLog.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    public void createLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logRepository.findAll().size();

        // Create the Log with an existing ID
        log.setId(1L);
        LogDTO logDTO = logMapper.toDto(log);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogMockMvc.perform(post("/api/logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = logRepository.findAll().size();
        // set the field null
        log.setDescription(null);

        // Create the Log, which fails.
        LogDTO logDTO = logMapper.toDto(log);


        restLogMockMvc.perform(post("/api/logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogs() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList
        restLogMockMvc.perform(get("/api/logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(log.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get the log
        restLogMockMvc.perform(get("/api/logs/{id}", log.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(log.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }


    @Test
    @Transactional
    public void getLogsByIdFiltering() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        Long id = log.getId();

        defaultLogShouldBeFound("id.equals=" + id);
        defaultLogShouldNotBeFound("id.notEquals=" + id);

        defaultLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLogShouldNotBeFound("id.greaterThan=" + id);

        defaultLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLogsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description equals to DEFAULT_DESCRIPTION
        defaultLogShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the logList where description equals to UPDATED_DESCRIPTION
        defaultLogShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLogsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description not equals to DEFAULT_DESCRIPTION
        defaultLogShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the logList where description not equals to UPDATED_DESCRIPTION
        defaultLogShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLogsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLogShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the logList where description equals to UPDATED_DESCRIPTION
        defaultLogShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLogsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description is not null
        defaultLogShouldBeFound("description.specified=true");

        // Get all the logList where description is null
        defaultLogShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLogsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description contains DEFAULT_DESCRIPTION
        defaultLogShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the logList where description contains UPDATED_DESCRIPTION
        defaultLogShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLogsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where description does not contain DEFAULT_DESCRIPTION
        defaultLogShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the logList where description does not contain UPDATED_DESCRIPTION
        defaultLogShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLogsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created equals to DEFAULT_CREATED
        defaultLogShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the logList where created equals to UPDATED_CREATED
        defaultLogShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created not equals to DEFAULT_CREATED
        defaultLogShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the logList where created not equals to UPDATED_CREATED
        defaultLogShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultLogShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the logList where created equals to UPDATED_CREATED
        defaultLogShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllLogsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        // Get all the logList where created is not null
        defaultLogShouldBeFound("created.specified=true");

        // Get all the logList where created is null
        defaultLogShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        LogType type = log.getType();
        logRepository.saveAndFlush(log);
        Long typeId = type.getId();

        // Get all the logList where type equals to typeId
        defaultLogShouldBeFound("typeId.equals=" + typeId);

        // Get all the logList where type equals to typeId + 1
        defaultLogShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllLogsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = log.getUser();
        logRepository.saveAndFlush(log);
        Long userId = user.getId();

        // Get all the logList where user equals to userId
        defaultLogShouldBeFound("userId.equals=" + userId);

        // Get all the logList where user equals to userId + 1
        defaultLogShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLogShouldBeFound(String filter) throws Exception {
        restLogMockMvc.perform(get("/api/logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(log.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));

        // Check, that the count call also returns 1
        restLogMockMvc.perform(get("/api/logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLogShouldNotBeFound(String filter) throws Exception {
        restLogMockMvc.perform(get("/api/logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLogMockMvc.perform(get("/api/logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLog() throws Exception {
        // Get the log
        restLogMockMvc.perform(get("/api/logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        int databaseSizeBeforeUpdate = logRepository.findAll().size();

        // Update the log
        Log updatedLog = logRepository.findById(log.getId()).get();
        // Disconnect from session so that the updates on updatedLog are not directly saved in db
        em.detach(updatedLog);
        updatedLog
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED);
        LogDTO logDTO = logMapper.toDto(updatedLog);

        restLogMockMvc.perform(put("/api/logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isOk());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeUpdate);
        Log testLog = logList.get(logList.size() - 1);
        assertThat(testLog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLog.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLog() throws Exception {
        int databaseSizeBeforeUpdate = logRepository.findAll().size();

        // Create the Log
        LogDTO logDTO = logMapper.toDto(log);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogMockMvc.perform(put("/api/logs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Log in the database
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLog() throws Exception {
        // Initialize the database
        logRepository.saveAndFlush(log);

        int databaseSizeBeforeDelete = logRepository.findAll().size();

        // Delete the log
        restLogMockMvc.perform(delete("/api/logs/{id}", log.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Log> logList = logRepository.findAll();
        assertThat(logList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
