package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Collection;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.CollectionRepository;
import com.intuite.shopped.service.CollectionService;
import com.intuite.shopped.service.dto.CollectionDTO;
import com.intuite.shopped.service.mapper.CollectionMapper;
import com.intuite.shopped.service.dto.CollectionCriteria;
import com.intuite.shopped.service.CollectionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link CollectionResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CollectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionQueryService collectionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectionMockMvc;

    private Collection collection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collection createEntity(EntityManager em) {
        Collection collection = new Collection()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .created(DEFAULT_CREATED)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        collection.setUser(user);
        return collection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collection createUpdatedEntity(EntityManager em) {
        Collection collection = new Collection()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        collection.setUser(user);
        return collection;
    }

    @BeforeEach
    public void initTest() {
        collection = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollection() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();
        // Create the Collection
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);
        restCollectionMockMvc.perform(post("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate + 1);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCollection.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testCollection.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCollection.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testCollection.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionRepository.findAll().size();

        // Create the Collection with an existing ID
        collection.setId(1L);
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionMockMvc.perform(post("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionRepository.findAll().size();
        // set the field null
        collection.setName(null);

        // Create the Collection, which fails.
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);


        restCollectionMockMvc.perform(post("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = collectionRepository.findAll().size();
        // set the field null
        collection.setDescription(null);

        // Create the Collection, which fails.
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);


        restCollectionMockMvc.perform(post("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollections() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList
        restCollectionMockMvc.perform(get("/api/collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", collection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getCollectionsByIdFiltering() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        Long id = collection.getId();

        defaultCollectionShouldBeFound("id.equals=" + id);
        defaultCollectionShouldNotBeFound("id.notEquals=" + id);

        defaultCollectionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollectionShouldNotBeFound("id.greaterThan=" + id);

        defaultCollectionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollectionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCollectionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name equals to DEFAULT_NAME
        defaultCollectionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the collectionList where name equals to UPDATED_NAME
        defaultCollectionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name not equals to DEFAULT_NAME
        defaultCollectionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the collectionList where name not equals to UPDATED_NAME
        defaultCollectionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCollectionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the collectionList where name equals to UPDATED_NAME
        defaultCollectionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name is not null
        defaultCollectionShouldBeFound("name.specified=true");

        // Get all the collectionList where name is null
        defaultCollectionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCollectionsByNameContainsSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name contains DEFAULT_NAME
        defaultCollectionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the collectionList where name contains UPDATED_NAME
        defaultCollectionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where name does not contain DEFAULT_NAME
        defaultCollectionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the collectionList where name does not contain UPDATED_NAME
        defaultCollectionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCollectionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description equals to DEFAULT_DESCRIPTION
        defaultCollectionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the collectionList where description equals to UPDATED_DESCRIPTION
        defaultCollectionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCollectionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description not equals to DEFAULT_DESCRIPTION
        defaultCollectionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the collectionList where description not equals to UPDATED_DESCRIPTION
        defaultCollectionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCollectionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCollectionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the collectionList where description equals to UPDATED_DESCRIPTION
        defaultCollectionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCollectionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description is not null
        defaultCollectionShouldBeFound("description.specified=true");

        // Get all the collectionList where description is null
        defaultCollectionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCollectionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description contains DEFAULT_DESCRIPTION
        defaultCollectionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the collectionList where description contains UPDATED_DESCRIPTION
        defaultCollectionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCollectionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where description does not contain DEFAULT_DESCRIPTION
        defaultCollectionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the collectionList where description does not contain UPDATED_DESCRIPTION
        defaultCollectionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCollectionsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where created equals to DEFAULT_CREATED
        defaultCollectionShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the collectionList where created equals to UPDATED_CREATED
        defaultCollectionShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllCollectionsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where created not equals to DEFAULT_CREATED
        defaultCollectionShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the collectionList where created not equals to UPDATED_CREATED
        defaultCollectionShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllCollectionsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultCollectionShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the collectionList where created equals to UPDATED_CREATED
        defaultCollectionShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllCollectionsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where created is not null
        defaultCollectionShouldBeFound("created.specified=true");

        // Get all the collectionList where created is null
        defaultCollectionShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllCollectionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where status equals to DEFAULT_STATUS
        defaultCollectionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the collectionList where status equals to UPDATED_STATUS
        defaultCollectionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where status not equals to DEFAULT_STATUS
        defaultCollectionShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the collectionList where status not equals to UPDATED_STATUS
        defaultCollectionShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCollectionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the collectionList where status equals to UPDATED_STATUS
        defaultCollectionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        // Get all the collectionList where status is not null
        defaultCollectionShouldBeFound("status.specified=true");

        // Get all the collectionList where status is null
        defaultCollectionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCollectionsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = collection.getUser();
        collectionRepository.saveAndFlush(collection);
        Long userId = user.getId();

        // Get all the collectionList where user equals to userId
        defaultCollectionShouldBeFound("userId.equals=" + userId);

        // Get all the collectionList where user equals to userId + 1
        defaultCollectionShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollectionShouldBeFound(String filter) throws Exception {
        restCollectionMockMvc.perform(get("/api/collections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCollectionMockMvc.perform(get("/api/collections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollectionShouldNotBeFound(String filter) throws Exception {
        restCollectionMockMvc.perform(get("/api/collections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollectionMockMvc.perform(get("/api/collections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCollection() throws Exception {
        // Get the collection
        restCollectionMockMvc.perform(get("/api/collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Update the collection
        Collection updatedCollection = collectionRepository.findById(collection.getId()).get();
        // Disconnect from session so that the updates on updatedCollection are not directly saved in db
        em.detach(updatedCollection);
        updatedCollection
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        CollectionDTO collectionDTO = collectionMapper.toDto(updatedCollection);

        restCollectionMockMvc.perform(put("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isOk());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate);
        Collection testCollection = collectionList.get(collectionList.size() - 1);
        assertThat(testCollection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCollection.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testCollection.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCollection.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testCollection.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCollection() throws Exception {
        int databaseSizeBeforeUpdate = collectionRepository.findAll().size();

        // Create the Collection
        CollectionDTO collectionDTO = collectionMapper.toDto(collection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionMockMvc.perform(put("/api/collections").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collection in the database
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollection() throws Exception {
        // Initialize the database
        collectionRepository.saveAndFlush(collection);

        int databaseSizeBeforeDelete = collectionRepository.findAll().size();

        // Delete the collection
        restCollectionMockMvc.perform(delete("/api/collections/{id}", collection.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collection> collectionList = collectionRepository.findAll();
        assertThat(collectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
