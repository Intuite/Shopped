package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.RecipeHasRecipeTag;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.domain.RecipeTag;
import com.intuite.shopped.repository.RecipeHasRecipeTagRepository;
import com.intuite.shopped.service.RecipeHasRecipeTagService;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagDTO;
import com.intuite.shopped.service.mapper.RecipeHasRecipeTagMapper;
import com.intuite.shopped.service.dto.RecipeHasRecipeTagCriteria;
import com.intuite.shopped.service.RecipeHasRecipeTagQueryService;

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
 * Integration tests for the {@link RecipeHasRecipeTagResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeHasRecipeTagResourceIT {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private RecipeHasRecipeTagRepository recipeHasRecipeTagRepository;

    @Autowired
    private RecipeHasRecipeTagMapper recipeHasRecipeTagMapper;

    @Autowired
    private RecipeHasRecipeTagService recipeHasRecipeTagService;

    @Autowired
    private RecipeHasRecipeTagQueryService recipeHasRecipeTagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeHasRecipeTagMockMvc;

    private RecipeHasRecipeTag recipeHasRecipeTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeHasRecipeTag createEntity(EntityManager em) {
        RecipeHasRecipeTag recipeHasRecipeTag = new RecipeHasRecipeTag()
            .status(DEFAULT_STATUS);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeHasRecipeTag.setRecipe(recipe);
        // Add required entity
        RecipeTag recipeTag;
        if (TestUtil.findAll(em, RecipeTag.class).isEmpty()) {
            recipeTag = RecipeTagResourceIT.createEntity(em);
            em.persist(recipeTag);
            em.flush();
        } else {
            recipeTag = TestUtil.findAll(em, RecipeTag.class).get(0);
        }
        recipeHasRecipeTag.setRecipeTag(recipeTag);
        return recipeHasRecipeTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeHasRecipeTag createUpdatedEntity(EntityManager em) {
        RecipeHasRecipeTag recipeHasRecipeTag = new RecipeHasRecipeTag()
            .status(UPDATED_STATUS);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createUpdatedEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeHasRecipeTag.setRecipe(recipe);
        // Add required entity
        RecipeTag recipeTag;
        if (TestUtil.findAll(em, RecipeTag.class).isEmpty()) {
            recipeTag = RecipeTagResourceIT.createUpdatedEntity(em);
            em.persist(recipeTag);
            em.flush();
        } else {
            recipeTag = TestUtil.findAll(em, RecipeTag.class).get(0);
        }
        recipeHasRecipeTag.setRecipeTag(recipeTag);
        return recipeHasRecipeTag;
    }

    @BeforeEach
    public void initTest() {
        recipeHasRecipeTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeHasRecipeTag() throws Exception {
        int databaseSizeBeforeCreate = recipeHasRecipeTagRepository.findAll().size();
        // Create the RecipeHasRecipeTag
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO = recipeHasRecipeTagMapper.toDto(recipeHasRecipeTag);
        restRecipeHasRecipeTagMockMvc.perform(post("/api/recipe-has-recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasRecipeTagDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeHasRecipeTag in the database
        List<RecipeHasRecipeTag> recipeHasRecipeTagList = recipeHasRecipeTagRepository.findAll();
        assertThat(recipeHasRecipeTagList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeHasRecipeTag testRecipeHasRecipeTag = recipeHasRecipeTagList.get(recipeHasRecipeTagList.size() - 1);
        assertThat(testRecipeHasRecipeTag.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRecipeHasRecipeTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeHasRecipeTagRepository.findAll().size();

        // Create the RecipeHasRecipeTag with an existing ID
        recipeHasRecipeTag.setId(1L);
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO = recipeHasRecipeTagMapper.toDto(recipeHasRecipeTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeHasRecipeTagMockMvc.perform(post("/api/recipe-has-recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasRecipeTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeHasRecipeTag in the database
        List<RecipeHasRecipeTag> recipeHasRecipeTagList = recipeHasRecipeTagRepository.findAll();
        assertThat(recipeHasRecipeTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecipeHasRecipeTags() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get all the recipeHasRecipeTagList
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeHasRecipeTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipeHasRecipeTag() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get the recipeHasRecipeTag
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags/{id}", recipeHasRecipeTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeHasRecipeTag.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getRecipeHasRecipeTagsByIdFiltering() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        Long id = recipeHasRecipeTag.getId();

        defaultRecipeHasRecipeTagShouldBeFound("id.equals=" + id);
        defaultRecipeHasRecipeTagShouldNotBeFound("id.notEquals=" + id);

        defaultRecipeHasRecipeTagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecipeHasRecipeTagShouldNotBeFound("id.greaterThan=" + id);

        defaultRecipeHasRecipeTagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecipeHasRecipeTagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get all the recipeHasRecipeTagList where status equals to DEFAULT_STATUS
        defaultRecipeHasRecipeTagShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the recipeHasRecipeTagList where status equals to UPDATED_STATUS
        defaultRecipeHasRecipeTagShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get all the recipeHasRecipeTagList where status not equals to DEFAULT_STATUS
        defaultRecipeHasRecipeTagShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the recipeHasRecipeTagList where status not equals to UPDATED_STATUS
        defaultRecipeHasRecipeTagShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get all the recipeHasRecipeTagList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRecipeHasRecipeTagShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the recipeHasRecipeTagList where status equals to UPDATED_STATUS
        defaultRecipeHasRecipeTagShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        // Get all the recipeHasRecipeTagList where status is not null
        defaultRecipeHasRecipeTagShouldBeFound("status.specified=true");

        // Get all the recipeHasRecipeTagList where status is null
        defaultRecipeHasRecipeTagShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByRecipeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recipe recipe = recipeHasRecipeTag.getRecipe();
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);
        Long recipeId = recipe.getId();

        // Get all the recipeHasRecipeTagList where recipe equals to recipeId
        defaultRecipeHasRecipeTagShouldBeFound("recipeId.equals=" + recipeId);

        // Get all the recipeHasRecipeTagList where recipe equals to recipeId + 1
        defaultRecipeHasRecipeTagShouldNotBeFound("recipeId.equals=" + (recipeId + 1));
    }


    @Test
    @Transactional
    public void getAllRecipeHasRecipeTagsByRecipeTagIsEqualToSomething() throws Exception {
        // Get already existing entity
        RecipeTag recipeTag = recipeHasRecipeTag.getRecipeTag();
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);
        Long recipeTagId = recipeTag.getId();

        // Get all the recipeHasRecipeTagList where recipeTag equals to recipeTagId
        defaultRecipeHasRecipeTagShouldBeFound("recipeTagId.equals=" + recipeTagId);

        // Get all the recipeHasRecipeTagList where recipeTag equals to recipeTagId + 1
        defaultRecipeHasRecipeTagShouldNotBeFound("recipeTagId.equals=" + (recipeTagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecipeHasRecipeTagShouldBeFound(String filter) throws Exception {
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeHasRecipeTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecipeHasRecipeTagShouldNotBeFound(String filter) throws Exception {
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeHasRecipeTag() throws Exception {
        // Get the recipeHasRecipeTag
        restRecipeHasRecipeTagMockMvc.perform(get("/api/recipe-has-recipe-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeHasRecipeTag() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        int databaseSizeBeforeUpdate = recipeHasRecipeTagRepository.findAll().size();

        // Update the recipeHasRecipeTag
        RecipeHasRecipeTag updatedRecipeHasRecipeTag = recipeHasRecipeTagRepository.findById(recipeHasRecipeTag.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeHasRecipeTag are not directly saved in db
        em.detach(updatedRecipeHasRecipeTag);
        updatedRecipeHasRecipeTag
            .status(UPDATED_STATUS);
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO = recipeHasRecipeTagMapper.toDto(updatedRecipeHasRecipeTag);

        restRecipeHasRecipeTagMockMvc.perform(put("/api/recipe-has-recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasRecipeTagDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeHasRecipeTag in the database
        List<RecipeHasRecipeTag> recipeHasRecipeTagList = recipeHasRecipeTagRepository.findAll();
        assertThat(recipeHasRecipeTagList).hasSize(databaseSizeBeforeUpdate);
        RecipeHasRecipeTag testRecipeHasRecipeTag = recipeHasRecipeTagList.get(recipeHasRecipeTagList.size() - 1);
        assertThat(testRecipeHasRecipeTag.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeHasRecipeTag() throws Exception {
        int databaseSizeBeforeUpdate = recipeHasRecipeTagRepository.findAll().size();

        // Create the RecipeHasRecipeTag
        RecipeHasRecipeTagDTO recipeHasRecipeTagDTO = recipeHasRecipeTagMapper.toDto(recipeHasRecipeTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeHasRecipeTagMockMvc.perform(put("/api/recipe-has-recipe-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasRecipeTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeHasRecipeTag in the database
        List<RecipeHasRecipeTag> recipeHasRecipeTagList = recipeHasRecipeTagRepository.findAll();
        assertThat(recipeHasRecipeTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeHasRecipeTag() throws Exception {
        // Initialize the database
        recipeHasRecipeTagRepository.saveAndFlush(recipeHasRecipeTag);

        int databaseSizeBeforeDelete = recipeHasRecipeTagRepository.findAll().size();

        // Delete the recipeHasRecipeTag
        restRecipeHasRecipeTagMockMvc.perform(delete("/api/recipe-has-recipe-tags/{id}", recipeHasRecipeTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipeHasRecipeTag> recipeHasRecipeTagList = recipeHasRecipeTagRepository.findAll();
        assertThat(recipeHasRecipeTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
