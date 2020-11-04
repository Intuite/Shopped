package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.IngredientTag;
import com.intuite.shopped.domain.TagType;
import com.intuite.shopped.repository.IngredientTagRepository;
import com.intuite.shopped.service.IngredientTagService;
import com.intuite.shopped.service.dto.IngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientTagMapper;
import com.intuite.shopped.service.dto.IngredientTagCriteria;
import com.intuite.shopped.service.IngredientTagQueryService;

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
 * Integration tests for the {@link IngredientTagResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IngredientTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private IngredientTagRepository ingredientTagRepository;

    @Autowired
    private IngredientTagMapper ingredientTagMapper;

    @Autowired
    private IngredientTagService ingredientTagService;

    @Autowired
    private IngredientTagQueryService ingredientTagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientTagMockMvc;

    private IngredientTag ingredientTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientTag createEntity(EntityManager em) {
        IngredientTag ingredientTag = new IngredientTag()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        // Add required entity
        TagType tagType;
        if (TestUtil.findAll(em, TagType.class).isEmpty()) {
            tagType = TagTypeResourceIT.createEntity(em);
            em.persist(tagType);
            em.flush();
        } else {
            tagType = TestUtil.findAll(em, TagType.class).get(0);
        }
        ingredientTag.setType(tagType);
        return ingredientTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientTag createUpdatedEntity(EntityManager em) {
        IngredientTag ingredientTag = new IngredientTag()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        // Add required entity
        TagType tagType;
        if (TestUtil.findAll(em, TagType.class).isEmpty()) {
            tagType = TagTypeResourceIT.createUpdatedEntity(em);
            em.persist(tagType);
            em.flush();
        } else {
            tagType = TestUtil.findAll(em, TagType.class).get(0);
        }
        ingredientTag.setType(tagType);
        return ingredientTag;
    }

    @BeforeEach
    public void initTest() {
        ingredientTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientTag() throws Exception {
        int databaseSizeBeforeCreate = ingredientTagRepository.findAll().size();
        // Create the IngredientTag
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(ingredientTag);
        restIngredientTagMockMvc.perform(post("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientTag in the database
        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientTag testIngredientTag = ingredientTagList.get(ingredientTagList.size() - 1);
        assertThat(testIngredientTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredientTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIngredientTag.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createIngredientTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientTagRepository.findAll().size();

        // Create the IngredientTag with an existing ID
        ingredientTag.setId(1L);
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(ingredientTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientTagMockMvc.perform(post("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientTag in the database
        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientTagRepository.findAll().size();
        // set the field null
        ingredientTag.setName(null);

        // Create the IngredientTag, which fails.
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(ingredientTag);


        restIngredientTagMockMvc.perform(post("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isBadRequest());

        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientTagRepository.findAll().size();
        // set the field null
        ingredientTag.setDescription(null);

        // Create the IngredientTag, which fails.
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(ingredientTag);


        restIngredientTagMockMvc.perform(post("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isBadRequest());

        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredientTags() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getIngredientTag() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get the ingredientTag
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags/{id}", ingredientTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getIngredientTagsByIdFiltering() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        Long id = ingredientTag.getId();

        defaultIngredientTagShouldBeFound("id.equals=" + id);
        defaultIngredientTagShouldNotBeFound("id.notEquals=" + id);

        defaultIngredientTagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIngredientTagShouldNotBeFound("id.greaterThan=" + id);

        defaultIngredientTagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIngredientTagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIngredientTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name equals to DEFAULT_NAME
        defaultIngredientTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ingredientTagList where name equals to UPDATED_NAME
        defaultIngredientTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name not equals to DEFAULT_NAME
        defaultIngredientTagShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ingredientTagList where name not equals to UPDATED_NAME
        defaultIngredientTagShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIngredientTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ingredientTagList where name equals to UPDATED_NAME
        defaultIngredientTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name is not null
        defaultIngredientTagShouldBeFound("name.specified=true");

        // Get all the ingredientTagList where name is null
        defaultIngredientTagShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllIngredientTagsByNameContainsSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name contains DEFAULT_NAME
        defaultIngredientTagShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ingredientTagList where name contains UPDATED_NAME
        defaultIngredientTagShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where name does not contain DEFAULT_NAME
        defaultIngredientTagShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ingredientTagList where name does not contain UPDATED_NAME
        defaultIngredientTagShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description equals to DEFAULT_DESCRIPTION
        defaultIngredientTagShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientTagList where description equals to UPDATED_DESCRIPTION
        defaultIngredientTagShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description not equals to DEFAULT_DESCRIPTION
        defaultIngredientTagShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientTagList where description not equals to UPDATED_DESCRIPTION
        defaultIngredientTagShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultIngredientTagShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ingredientTagList where description equals to UPDATED_DESCRIPTION
        defaultIngredientTagShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description is not null
        defaultIngredientTagShouldBeFound("description.specified=true");

        // Get all the ingredientTagList where description is null
        defaultIngredientTagShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description contains DEFAULT_DESCRIPTION
        defaultIngredientTagShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientTagList where description contains UPDATED_DESCRIPTION
        defaultIngredientTagShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where description does not contain DEFAULT_DESCRIPTION
        defaultIngredientTagShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientTagList where description does not contain UPDATED_DESCRIPTION
        defaultIngredientTagShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllIngredientTagsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where status equals to DEFAULT_STATUS
        defaultIngredientTagShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ingredientTagList where status equals to UPDATED_STATUS
        defaultIngredientTagShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where status not equals to DEFAULT_STATUS
        defaultIngredientTagShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ingredientTagList where status not equals to UPDATED_STATUS
        defaultIngredientTagShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIngredientTagShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ingredientTagList where status equals to UPDATED_STATUS
        defaultIngredientTagShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        // Get all the ingredientTagList where status is not null
        defaultIngredientTagShouldBeFound("status.specified=true");

        // Get all the ingredientTagList where status is null
        defaultIngredientTagShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllIngredientTagsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        TagType type = ingredientTag.getType();
        ingredientTagRepository.saveAndFlush(ingredientTag);
        Long typeId = type.getId();

        // Get all the ingredientTagList where type equals to typeId
        defaultIngredientTagShouldBeFound("typeId.equals=" + typeId);

        // Get all the ingredientTagList where type equals to typeId + 1
        defaultIngredientTagShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIngredientTagShouldBeFound(String filter) throws Exception {
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIngredientTagShouldNotBeFound(String filter) throws Exception {
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientTag() throws Exception {
        // Get the ingredientTag
        restIngredientTagMockMvc.perform(get("/api/ingredient-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientTag() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        int databaseSizeBeforeUpdate = ingredientTagRepository.findAll().size();

        // Update the ingredientTag
        IngredientTag updatedIngredientTag = ingredientTagRepository.findById(ingredientTag.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientTag are not directly saved in db
        em.detach(updatedIngredientTag);
        updatedIngredientTag
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(updatedIngredientTag);

        restIngredientTagMockMvc.perform(put("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientTag in the database
        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeUpdate);
        IngredientTag testIngredientTag = ingredientTagList.get(ingredientTagList.size() - 1);
        assertThat(testIngredientTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredientTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIngredientTag.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientTag() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTagRepository.findAll().size();

        // Create the IngredientTag
        IngredientTagDTO ingredientTagDTO = ingredientTagMapper.toDto(ingredientTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientTagMockMvc.perform(put("/api/ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientTag in the database
        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientTag() throws Exception {
        // Initialize the database
        ingredientTagRepository.saveAndFlush(ingredientTag);

        int databaseSizeBeforeDelete = ingredientTagRepository.findAll().size();

        // Delete the ingredientTag
        restIngredientTagMockMvc.perform(delete("/api/ingredient-tags/{id}", ingredientTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientTag> ingredientTagList = ingredientTagRepository.findAll();
        assertThat(ingredientTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
