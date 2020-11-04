package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.TagType;
import com.intuite.shopped.repository.TagTypeRepository;
import com.intuite.shopped.service.TagTypeService;
import com.intuite.shopped.service.dto.TagTypeDTO;
import com.intuite.shopped.service.mapper.TagTypeMapper;
import com.intuite.shopped.service.dto.TagTypeCriteria;
import com.intuite.shopped.service.TagTypeQueryService;

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
 * Integration tests for the {@link TagTypeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TagTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private TagTypeRepository tagTypeRepository;

    @Autowired
    private TagTypeMapper tagTypeMapper;

    @Autowired
    private TagTypeService tagTypeService;

    @Autowired
    private TagTypeQueryService tagTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagTypeMockMvc;

    private TagType tagType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagType createEntity(EntityManager em) {
        TagType tagType = new TagType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return tagType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagType createUpdatedEntity(EntityManager em) {
        TagType tagType = new TagType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return tagType;
    }

    @BeforeEach
    public void initTest() {
        tagType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagType() throws Exception {
        int databaseSizeBeforeCreate = tagTypeRepository.findAll().size();
        // Create the TagType
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(tagType);
        restTagTypeMockMvc.perform(post("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TagType testTagType = tagTypeList.get(tagTypeList.size() - 1);
        assertThat(testTagType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTagType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTagType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTagTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagTypeRepository.findAll().size();

        // Create the TagType with an existing ID
        tagType.setId(1L);
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(tagType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagTypeMockMvc.perform(post("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagTypeRepository.findAll().size();
        // set the field null
        tagType.setName(null);

        // Create the TagType, which fails.
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(tagType);


        restTagTypeMockMvc.perform(post("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tagTypeRepository.findAll().size();
        // set the field null
        tagType.setDescription(null);

        // Create the TagType, which fails.
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(tagType);


        restTagTypeMockMvc.perform(post("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTagTypes() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTagType() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get the tagType
        restTagTypeMockMvc.perform(get("/api/tag-types/{id}", tagType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getTagTypesByIdFiltering() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        Long id = tagType.getId();

        defaultTagTypeShouldBeFound("id.equals=" + id);
        defaultTagTypeShouldNotBeFound("id.notEquals=" + id);

        defaultTagTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTagTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultTagTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTagTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTagTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name equals to DEFAULT_NAME
        defaultTagTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tagTypeList where name equals to UPDATED_NAME
        defaultTagTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name not equals to DEFAULT_NAME
        defaultTagTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tagTypeList where name not equals to UPDATED_NAME
        defaultTagTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTagTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tagTypeList where name equals to UPDATED_NAME
        defaultTagTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name is not null
        defaultTagTypeShouldBeFound("name.specified=true");

        // Get all the tagTypeList where name is null
        defaultTagTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTagTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name contains DEFAULT_NAME
        defaultTagTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tagTypeList where name contains UPDATED_NAME
        defaultTagTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name does not contain DEFAULT_NAME
        defaultTagTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tagTypeList where name does not contain UPDATED_NAME
        defaultTagTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description equals to DEFAULT_DESCRIPTION
        defaultTagTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tagTypeList where description equals to UPDATED_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the tagTypeList where description not equals to UPDATED_DESCRIPTION
        defaultTagTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTagTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tagTypeList where description equals to UPDATED_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description is not null
        defaultTagTypeShouldBeFound("description.specified=true");

        // Get all the tagTypeList where description is null
        defaultTagTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTagTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description contains DEFAULT_DESCRIPTION
        defaultTagTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the tagTypeList where description contains UPDATED_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the tagTypeList where description does not contain UPDATED_DESCRIPTION
        defaultTagTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTagTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where status equals to DEFAULT_STATUS
        defaultTagTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the tagTypeList where status equals to UPDATED_STATUS
        defaultTagTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTagTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where status not equals to DEFAULT_STATUS
        defaultTagTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the tagTypeList where status not equals to UPDATED_STATUS
        defaultTagTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTagTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTagTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the tagTypeList where status equals to UPDATED_STATUS
        defaultTagTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTagTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where status is not null
        defaultTagTypeShouldBeFound("status.specified=true");

        // Get all the tagTypeList where status is null
        defaultTagTypeShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTagTypeShouldBeFound(String filter) throws Exception {
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restTagTypeMockMvc.perform(get("/api/tag-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTagTypeShouldNotBeFound(String filter) throws Exception {
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTagTypeMockMvc.perform(get("/api/tag-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTagType() throws Exception {
        // Get the tagType
        restTagTypeMockMvc.perform(get("/api/tag-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagType() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        int databaseSizeBeforeUpdate = tagTypeRepository.findAll().size();

        // Update the tagType
        TagType updatedTagType = tagTypeRepository.findById(tagType.getId()).get();
        // Disconnect from session so that the updates on updatedTagType are not directly saved in db
        em.detach(updatedTagType);
        updatedTagType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(updatedTagType);

        restTagTypeMockMvc.perform(put("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeUpdate);
        TagType testTagType = tagTypeList.get(tagTypeList.size() - 1);
        assertThat(testTagType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTagType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTagType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTagType() throws Exception {
        int databaseSizeBeforeUpdate = tagTypeRepository.findAll().size();

        // Create the TagType
        TagTypeDTO tagTypeDTO = tagTypeMapper.toDto(tagType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagTypeMockMvc.perform(put("/api/tag-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagType() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        int databaseSizeBeforeDelete = tagTypeRepository.findAll().size();

        // Delete the tagType
        restTagTypeMockMvc.perform(delete("/api/tag-types/{id}", tagType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
