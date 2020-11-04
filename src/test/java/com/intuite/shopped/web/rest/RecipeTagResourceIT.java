package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.RecipeTag;
import com.intuite.shopped.domain.TagType;
import com.intuite.shopped.repository.RecipeTagRepository;
import com.intuite.shopped.service.RecipeTagService;
import com.intuite.shopped.service.dto.RecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeTagMapper;
import com.intuite.shopped.service.dto.RecipeTagCriteria;
import com.intuite.shopped.service.RecipeTagQueryService;

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
 * Integration tests for the {@link RecipeTagResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private RecipeTagRepository recipeTagRepository;

    @Autowired
    private RecipeTagMapper recipeTagMapper;

    @Autowired
    private RecipeTagService recipeTagService;

    @Autowired
    private RecipeTagQueryService recipeTagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeTagMockMvc;

    private RecipeTag recipeTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeTag createEntity(EntityManager em) {
        RecipeTag recipeTag = new RecipeTag()
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
        recipeTag.setType(tagType);
        return recipeTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeTag createUpdatedEntity(EntityManager em) {
        RecipeTag recipeTag = new RecipeTag()
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
        recipeTag.setType(tagType);
        return recipeTag;
    }

    @BeforeEach
    public void initTest() {
        recipeTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeTag() throws Exception {
        int databaseSizeBeforeCreate = recipeTagRepository.findAll().size();
        // Create the RecipeTag
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(recipeTag);
        restRecipeTagMockMvc.perform(post("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeTag in the database
        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeTag testRecipeTag = recipeTagList.get(recipeTagList.size() - 1);
        assertThat(testRecipeTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecipeTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecipeTag.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRecipeTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeTagRepository.findAll().size();

        // Create the RecipeTag with an existing ID
        recipeTag.setId(1L);
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(recipeTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeTagMockMvc.perform(post("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeTag in the database
        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeTagRepository.findAll().size();
        // set the field null
        recipeTag.setName(null);

        // Create the RecipeTag, which fails.
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(recipeTag);


        restRecipeTagMockMvc.perform(post("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isBadRequest());

        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeTagRepository.findAll().size();
        // set the field null
        recipeTag.setDescription(null);

        // Create the RecipeTag, which fails.
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(recipeTag);


        restRecipeTagMockMvc.perform(post("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isBadRequest());

        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipeTags() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList
        restRecipeTagMockMvc.perform(get("/api/recipe-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipeTag() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get the recipeTag
        restRecipeTagMockMvc.perform(get("/api/recipe-tags/{id}", recipeTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getRecipeTagsByIdFiltering() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        Long id = recipeTag.getId();

        defaultRecipeTagShouldBeFound("id.equals=" + id);
        defaultRecipeTagShouldNotBeFound("id.notEquals=" + id);

        defaultRecipeTagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecipeTagShouldNotBeFound("id.greaterThan=" + id);

        defaultRecipeTagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecipeTagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecipeTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name equals to DEFAULT_NAME
        defaultRecipeTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the recipeTagList where name equals to UPDATED_NAME
        defaultRecipeTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name not equals to DEFAULT_NAME
        defaultRecipeTagShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the recipeTagList where name not equals to UPDATED_NAME
        defaultRecipeTagShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRecipeTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the recipeTagList where name equals to UPDATED_NAME
        defaultRecipeTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name is not null
        defaultRecipeTagShouldBeFound("name.specified=true");

        // Get all the recipeTagList where name is null
        defaultRecipeTagShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecipeTagsByNameContainsSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name contains DEFAULT_NAME
        defaultRecipeTagShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the recipeTagList where name contains UPDATED_NAME
        defaultRecipeTagShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where name does not contain DEFAULT_NAME
        defaultRecipeTagShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the recipeTagList where name does not contain UPDATED_NAME
        defaultRecipeTagShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description equals to DEFAULT_DESCRIPTION
        defaultRecipeTagShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the recipeTagList where description equals to UPDATED_DESCRIPTION
        defaultRecipeTagShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description not equals to DEFAULT_DESCRIPTION
        defaultRecipeTagShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the recipeTagList where description not equals to UPDATED_DESCRIPTION
        defaultRecipeTagShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRecipeTagShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the recipeTagList where description equals to UPDATED_DESCRIPTION
        defaultRecipeTagShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description is not null
        defaultRecipeTagShouldBeFound("description.specified=true");

        // Get all the recipeTagList where description is null
        defaultRecipeTagShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description contains DEFAULT_DESCRIPTION
        defaultRecipeTagShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the recipeTagList where description contains UPDATED_DESCRIPTION
        defaultRecipeTagShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where description does not contain DEFAULT_DESCRIPTION
        defaultRecipeTagShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the recipeTagList where description does not contain UPDATED_DESCRIPTION
        defaultRecipeTagShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllRecipeTagsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where status equals to DEFAULT_STATUS
        defaultRecipeTagShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the recipeTagList where status equals to UPDATED_STATUS
        defaultRecipeTagShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where status not equals to DEFAULT_STATUS
        defaultRecipeTagShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the recipeTagList where status not equals to UPDATED_STATUS
        defaultRecipeTagShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRecipeTagShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the recipeTagList where status equals to UPDATED_STATUS
        defaultRecipeTagShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        // Get all the recipeTagList where status is not null
        defaultRecipeTagShouldBeFound("status.specified=true");

        // Get all the recipeTagList where status is null
        defaultRecipeTagShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipeTagsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        TagType type = recipeTag.getType();
        recipeTagRepository.saveAndFlush(recipeTag);
        Long typeId = type.getId();

        // Get all the recipeTagList where type equals to typeId
        defaultRecipeTagShouldBeFound("typeId.equals=" + typeId);

        // Get all the recipeTagList where type equals to typeId + 1
        defaultRecipeTagShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecipeTagShouldBeFound(String filter) throws Exception {
        restRecipeTagMockMvc.perform(get("/api/recipe-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restRecipeTagMockMvc.perform(get("/api/recipe-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecipeTagShouldNotBeFound(String filter) throws Exception {
        restRecipeTagMockMvc.perform(get("/api/recipe-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecipeTagMockMvc.perform(get("/api/recipe-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeTag() throws Exception {
        // Get the recipeTag
        restRecipeTagMockMvc.perform(get("/api/recipe-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeTag() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        int databaseSizeBeforeUpdate = recipeTagRepository.findAll().size();

        // Update the recipeTag
        RecipeTag updatedRecipeTag = recipeTagRepository.findById(recipeTag.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeTag are not directly saved in db
        em.detach(updatedRecipeTag);
        updatedRecipeTag
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(updatedRecipeTag);

        restRecipeTagMockMvc.perform(put("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeTag in the database
        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeUpdate);
        RecipeTag testRecipeTag = recipeTagList.get(recipeTagList.size() - 1);
        assertThat(testRecipeTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecipeTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecipeTag.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeTag() throws Exception {
        int databaseSizeBeforeUpdate = recipeTagRepository.findAll().size();

        // Create the RecipeTag
        RecipeTagDTO recipeTagDTO = recipeTagMapper.toDto(recipeTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeTagMockMvc.perform(put("/api/recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeTag in the database
        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeTag() throws Exception {
        // Initialize the database
        recipeTagRepository.saveAndFlush(recipeTag);

        int databaseSizeBeforeDelete = recipeTagRepository.findAll().size();

        // Delete the recipeTag
        restRecipeTagMockMvc.perform(delete("/api/recipe-tags/{id}", recipeTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipeTag> recipeTagList = recipeTagRepository.findAll();
        assertThat(recipeTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
