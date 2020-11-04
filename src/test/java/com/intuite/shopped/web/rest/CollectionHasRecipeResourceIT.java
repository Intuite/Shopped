package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.CollectionHasRecipe;
import com.intuite.shopped.domain.Collection;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.repository.CollectionHasRecipeRepository;
import com.intuite.shopped.service.CollectionHasRecipeService;
import com.intuite.shopped.service.dto.CollectionHasRecipeDTO;
import com.intuite.shopped.service.mapper.CollectionHasRecipeMapper;
import com.intuite.shopped.service.dto.CollectionHasRecipeCriteria;
import com.intuite.shopped.service.CollectionHasRecipeQueryService;

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
 * Integration tests for the {@link CollectionHasRecipeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CollectionHasRecipeResourceIT {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private CollectionHasRecipeRepository collectionHasRecipeRepository;

    @Autowired
    private CollectionHasRecipeMapper collectionHasRecipeMapper;

    @Autowired
    private CollectionHasRecipeService collectionHasRecipeService;

    @Autowired
    private CollectionHasRecipeQueryService collectionHasRecipeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectionHasRecipeMockMvc;

    private CollectionHasRecipe collectionHasRecipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectionHasRecipe createEntity(EntityManager em) {
        CollectionHasRecipe collectionHasRecipe = new CollectionHasRecipe()
            .status(DEFAULT_STATUS);
        // Add required entity
        Collection collection;
        if (TestUtil.findAll(em, Collection.class).isEmpty()) {
            collection = CollectionResourceIT.createEntity(em);
            em.persist(collection);
            em.flush();
        } else {
            collection = TestUtil.findAll(em, Collection.class).get(0);
        }
        collectionHasRecipe.setCollection(collection);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        collectionHasRecipe.setRecipe(recipe);
        return collectionHasRecipe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectionHasRecipe createUpdatedEntity(EntityManager em) {
        CollectionHasRecipe collectionHasRecipe = new CollectionHasRecipe()
            .status(UPDATED_STATUS);
        // Add required entity
        Collection collection;
        if (TestUtil.findAll(em, Collection.class).isEmpty()) {
            collection = CollectionResourceIT.createUpdatedEntity(em);
            em.persist(collection);
            em.flush();
        } else {
            collection = TestUtil.findAll(em, Collection.class).get(0);
        }
        collectionHasRecipe.setCollection(collection);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createUpdatedEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        collectionHasRecipe.setRecipe(recipe);
        return collectionHasRecipe;
    }

    @BeforeEach
    public void initTest() {
        collectionHasRecipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollectionHasRecipe() throws Exception {
        int databaseSizeBeforeCreate = collectionHasRecipeRepository.findAll().size();
        // Create the CollectionHasRecipe
        CollectionHasRecipeDTO collectionHasRecipeDTO = collectionHasRecipeMapper.toDto(collectionHasRecipe);
        restCollectionHasRecipeMockMvc.perform(post("/api/collection-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionHasRecipeDTO)))
            .andExpect(status().isCreated());

        // Validate the CollectionHasRecipe in the database
        List<CollectionHasRecipe> collectionHasRecipeList = collectionHasRecipeRepository.findAll();
        assertThat(collectionHasRecipeList).hasSize(databaseSizeBeforeCreate + 1);
        CollectionHasRecipe testCollectionHasRecipe = collectionHasRecipeList.get(collectionHasRecipeList.size() - 1);
        assertThat(testCollectionHasRecipe.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCollectionHasRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionHasRecipeRepository.findAll().size();

        // Create the CollectionHasRecipe with an existing ID
        collectionHasRecipe.setId(1L);
        CollectionHasRecipeDTO collectionHasRecipeDTO = collectionHasRecipeMapper.toDto(collectionHasRecipe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionHasRecipeMockMvc.perform(post("/api/collection-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionHasRecipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionHasRecipe in the database
        List<CollectionHasRecipe> collectionHasRecipeList = collectionHasRecipeRepository.findAll();
        assertThat(collectionHasRecipeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCollectionHasRecipes() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get all the collectionHasRecipeList
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionHasRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCollectionHasRecipe() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get the collectionHasRecipe
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes/{id}", collectionHasRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectionHasRecipe.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getCollectionHasRecipesByIdFiltering() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        Long id = collectionHasRecipe.getId();

        defaultCollectionHasRecipeShouldBeFound("id.equals=" + id);
        defaultCollectionHasRecipeShouldNotBeFound("id.notEquals=" + id);

        defaultCollectionHasRecipeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollectionHasRecipeShouldNotBeFound("id.greaterThan=" + id);

        defaultCollectionHasRecipeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollectionHasRecipeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCollectionHasRecipesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get all the collectionHasRecipeList where status equals to DEFAULT_STATUS
        defaultCollectionHasRecipeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the collectionHasRecipeList where status equals to UPDATED_STATUS
        defaultCollectionHasRecipeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionHasRecipesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get all the collectionHasRecipeList where status not equals to DEFAULT_STATUS
        defaultCollectionHasRecipeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the collectionHasRecipeList where status not equals to UPDATED_STATUS
        defaultCollectionHasRecipeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionHasRecipesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get all the collectionHasRecipeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCollectionHasRecipeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the collectionHasRecipeList where status equals to UPDATED_STATUS
        defaultCollectionHasRecipeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCollectionHasRecipesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        // Get all the collectionHasRecipeList where status is not null
        defaultCollectionHasRecipeShouldBeFound("status.specified=true");

        // Get all the collectionHasRecipeList where status is null
        defaultCollectionHasRecipeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCollectionHasRecipesByCollectionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Collection collection = collectionHasRecipe.getCollection();
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);
        Long collectionId = collection.getId();

        // Get all the collectionHasRecipeList where collection equals to collectionId
        defaultCollectionHasRecipeShouldBeFound("collectionId.equals=" + collectionId);

        // Get all the collectionHasRecipeList where collection equals to collectionId + 1
        defaultCollectionHasRecipeShouldNotBeFound("collectionId.equals=" + (collectionId + 1));
    }


    @Test
    @Transactional
    public void getAllCollectionHasRecipesByRecipeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recipe recipe = collectionHasRecipe.getRecipe();
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);
        Long recipeId = recipe.getId();

        // Get all the collectionHasRecipeList where recipe equals to recipeId
        defaultCollectionHasRecipeShouldBeFound("recipeId.equals=" + recipeId);

        // Get all the collectionHasRecipeList where recipe equals to recipeId + 1
        defaultCollectionHasRecipeShouldNotBeFound("recipeId.equals=" + (recipeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollectionHasRecipeShouldBeFound(String filter) throws Exception {
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionHasRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollectionHasRecipeShouldNotBeFound(String filter) throws Exception {
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCollectionHasRecipe() throws Exception {
        // Get the collectionHasRecipe
        restCollectionHasRecipeMockMvc.perform(get("/api/collection-has-recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectionHasRecipe() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        int databaseSizeBeforeUpdate = collectionHasRecipeRepository.findAll().size();

        // Update the collectionHasRecipe
        CollectionHasRecipe updatedCollectionHasRecipe = collectionHasRecipeRepository.findById(collectionHasRecipe.getId()).get();
        // Disconnect from session so that the updates on updatedCollectionHasRecipe are not directly saved in db
        em.detach(updatedCollectionHasRecipe);
        updatedCollectionHasRecipe
            .status(UPDATED_STATUS);
        CollectionHasRecipeDTO collectionHasRecipeDTO = collectionHasRecipeMapper.toDto(updatedCollectionHasRecipe);

        restCollectionHasRecipeMockMvc.perform(put("/api/collection-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionHasRecipeDTO)))
            .andExpect(status().isOk());

        // Validate the CollectionHasRecipe in the database
        List<CollectionHasRecipe> collectionHasRecipeList = collectionHasRecipeRepository.findAll();
        assertThat(collectionHasRecipeList).hasSize(databaseSizeBeforeUpdate);
        CollectionHasRecipe testCollectionHasRecipe = collectionHasRecipeList.get(collectionHasRecipeList.size() - 1);
        assertThat(testCollectionHasRecipe.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCollectionHasRecipe() throws Exception {
        int databaseSizeBeforeUpdate = collectionHasRecipeRepository.findAll().size();

        // Create the CollectionHasRecipe
        CollectionHasRecipeDTO collectionHasRecipeDTO = collectionHasRecipeMapper.toDto(collectionHasRecipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionHasRecipeMockMvc.perform(put("/api/collection-has-recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collectionHasRecipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionHasRecipe in the database
        List<CollectionHasRecipe> collectionHasRecipeList = collectionHasRecipeRepository.findAll();
        assertThat(collectionHasRecipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollectionHasRecipe() throws Exception {
        // Initialize the database
        collectionHasRecipeRepository.saveAndFlush(collectionHasRecipe);

        int databaseSizeBeforeDelete = collectionHasRecipeRepository.findAll().size();

        // Delete the collectionHasRecipe
        restCollectionHasRecipeMockMvc.perform(delete("/api/collection-has-recipes/{id}", collectionHasRecipe.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollectionHasRecipe> collectionHasRecipeList = collectionHasRecipeRepository.findAll();
        assertThat(collectionHasRecipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
