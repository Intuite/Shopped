package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.NotificationType;
import com.intuite.shopped.repository.NotificationTypeRepository;
import com.intuite.shopped.service.NotificationTypeService;
import com.intuite.shopped.service.dto.NotificationTypeDTO;
import com.intuite.shopped.service.mapper.NotificationTypeMapper;
import com.intuite.shopped.service.dto.NotificationTypeCriteria;
import com.intuite.shopped.service.NotificationTypeQueryService;

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
 * Integration tests for the {@link NotificationTypeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificationTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private NotificationTypeMapper notificationTypeMapper;

    @Autowired
    private NotificationTypeService notificationTypeService;

    @Autowired
    private NotificationTypeQueryService notificationTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationTypeMockMvc;

    private NotificationType notificationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationType createEntity(EntityManager em) {
        NotificationType notificationType = new NotificationType()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return notificationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationType createUpdatedEntity(EntityManager em) {
        NotificationType notificationType = new NotificationType()
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        return notificationType;
    }

    @BeforeEach
    public void initTest() {
        notificationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificationType() throws Exception {
        int databaseSizeBeforeCreate = notificationTypeRepository.findAll().size();
        // Create the NotificationType
        NotificationTypeDTO notificationTypeDTO = notificationTypeMapper.toDto(notificationType);
        restNotificationTypeMockMvc.perform(post("/api/notification-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createNotificationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationTypeRepository.findAll().size();

        // Create the NotificationType with an existing ID
        notificationType.setId(1L);
        NotificationTypeDTO notificationTypeDTO = notificationTypeMapper.toDto(notificationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationTypeMockMvc.perform(post("/api/notification-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationTypeRepository.findAll().size();
        // set the field null
        notificationType.setName(null);

        // Create the NotificationType, which fails.
        NotificationTypeDTO notificationTypeDTO = notificationTypeMapper.toDto(notificationType);


        restNotificationTypeMockMvc.perform(post("/api/notification-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificationTypes() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList
        restNotificationTypeMockMvc.perform(get("/api/notification-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get the notificationType
        restNotificationTypeMockMvc.perform(get("/api/notification-types/{id}", notificationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getNotificationTypesByIdFiltering() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        Long id = notificationType.getId();

        defaultNotificationTypeShouldBeFound("id.equals=" + id);
        defaultNotificationTypeShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificationTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name equals to DEFAULT_NAME
        defaultNotificationTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the notificationTypeList where name equals to UPDATED_NAME
        defaultNotificationTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name not equals to DEFAULT_NAME
        defaultNotificationTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the notificationTypeList where name not equals to UPDATED_NAME
        defaultNotificationTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNotificationTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the notificationTypeList where name equals to UPDATED_NAME
        defaultNotificationTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name is not null
        defaultNotificationTypeShouldBeFound("name.specified=true");

        // Get all the notificationTypeList where name is null
        defaultNotificationTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name contains DEFAULT_NAME
        defaultNotificationTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the notificationTypeList where name contains UPDATED_NAME
        defaultNotificationTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where name does not contain DEFAULT_NAME
        defaultNotificationTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the notificationTypeList where name does not contain UPDATED_NAME
        defaultNotificationTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllNotificationTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where status equals to DEFAULT_STATUS
        defaultNotificationTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the notificationTypeList where status equals to UPDATED_STATUS
        defaultNotificationTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where status not equals to DEFAULT_STATUS
        defaultNotificationTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the notificationTypeList where status not equals to UPDATED_STATUS
        defaultNotificationTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNotificationTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the notificationTypeList where status equals to UPDATED_STATUS
        defaultNotificationTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        // Get all the notificationTypeList where status is not null
        defaultNotificationTypeShouldBeFound("status.specified=true");

        // Get all the notificationTypeList where status is null
        defaultNotificationTypeShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationTypeShouldBeFound(String filter) throws Exception {
        restNotificationTypeMockMvc.perform(get("/api/notification-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restNotificationTypeMockMvc.perform(get("/api/notification-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationTypeShouldNotBeFound(String filter) throws Exception {
        restNotificationTypeMockMvc.perform(get("/api/notification-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationTypeMockMvc.perform(get("/api/notification-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotificationType() throws Exception {
        // Get the notificationType
        restNotificationTypeMockMvc.perform(get("/api/notification-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();

        // Update the notificationType
        NotificationType updatedNotificationType = notificationTypeRepository.findById(notificationType.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationType are not directly saved in db
        em.detach(updatedNotificationType);
        updatedNotificationType
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        NotificationTypeDTO notificationTypeDTO = notificationTypeMapper.toDto(updatedNotificationType);

        restNotificationTypeMockMvc.perform(put("/api/notification-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
        NotificationType testNotificationType = notificationTypeList.get(notificationTypeList.size() - 1);
        assertThat(testNotificationType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificationType() throws Exception {
        int databaseSizeBeforeUpdate = notificationTypeRepository.findAll().size();

        // Create the NotificationType
        NotificationTypeDTO notificationTypeDTO = notificationTypeMapper.toDto(notificationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTypeMockMvc.perform(put("/api/notification-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationType in the database
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificationType() throws Exception {
        // Initialize the database
        notificationTypeRepository.saveAndFlush(notificationType);

        int databaseSizeBeforeDelete = notificationTypeRepository.findAll().size();

        // Delete the notificationType
        restNotificationTypeMockMvc.perform(delete("/api/notification-types/{id}", notificationType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationType> notificationTypeList = notificationTypeRepository.findAll();
        assertThat(notificationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
