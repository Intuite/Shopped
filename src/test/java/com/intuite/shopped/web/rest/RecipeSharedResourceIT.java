package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.RecipeShared;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.repository.RecipeSharedRepository;
import com.intuite.shopped.service.RecipeSharedService;
import com.intuite.shopped.service.dto.RecipeSharedDTO;
import com.intuite.shopped.service.mapper.RecipeSharedMapper;
import com.intuite.shopped.service.dto.RecipeSharedCriteria;
import com.intuite.shopped.service.RecipeSharedQueryService;

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
 * Integration tests for the {@link RecipeSharedResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeSharedResourceIT {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private RecipeSharedRepository recipeSharedRepository;

    @Autowired
    private RecipeSharedMapper recipeSharedMapper;

    @Autowired
    private RecipeSharedService recipeSharedService;

    @Autowired
    private RecipeSharedQueryService recipeSharedQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeSharedMockMvc;

    private RecipeShared recipeShared;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeShared createEntity(EntityManager em) {
        RecipeShared recipeShared = new RecipeShared()
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
        recipeShared.setRecipe(recipe);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        recipeShared.setUser(user);
        return recipeShared;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeShared createUpdatedEntity(EntityManager em) {
        RecipeShared recipeShared = new RecipeShared()
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
        recipeShared.setRecipe(recipe);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        recipeShared.setUser(user);
        return recipeShared;
    }

    @BeforeEach
    public void initTest() {
        recipeShared = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeShared() throws Exception {
        int databaseSizeBeforeCreate = recipeSharedRepository.findAll().size();
        // Create the RecipeShared
        RecipeSharedDTO recipeSharedDTO = recipeSharedMapper.toDto(recipeShared);
        restRecipeSharedMockMvc.perform(post("/api/recipe-shareds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeSharedDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeShared in the database
        List<RecipeShared> recipeSharedList = recipeSharedRepository.findAll();
        assertThat(recipeSharedList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeShared testRecipeShared = recipeSharedList.get(recipeSharedList.size() - 1);
        assertThat(testRecipeShared.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRecipeSharedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeSharedRepository.findAll().size();

        // Create the RecipeShared with an existing ID
        recipeShared.setId(1L);
        RecipeSharedDTO recipeSharedDTO = recipeSharedMapper.toDto(recipeShared);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeSharedMockMvc.perform(post("/api/recipe-shareds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeSharedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeShared in the database
        List<RecipeShared> recipeSharedList = recipeSharedRepository.findAll();
        assertThat(recipeSharedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecipeShareds() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get all the recipeSharedList
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeShared.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipeShared() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get the recipeShared
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds/{id}", recipeShared.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeShared.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getRecipeSharedsByIdFiltering() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        Long id = recipeShared.getId();

        defaultRecipeSharedShouldBeFound("id.equals=" + id);
        defaultRecipeSharedShouldNotBeFound("id.notEquals=" + id);

        defaultRecipeSharedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecipeSharedShouldNotBeFound("id.greaterThan=" + id);

        defaultRecipeSharedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecipeSharedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecipeSharedsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get all the recipeSharedList where status equals to DEFAULT_STATUS
        defaultRecipeSharedShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the recipeSharedList where status equals to UPDATED_STATUS
        defaultRecipeSharedShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeSharedsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get all the recipeSharedList where status not equals to DEFAULT_STATUS
        defaultRecipeSharedShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the recipeSharedList where status not equals to UPDATED_STATUS
        defaultRecipeSharedShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeSharedsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get all the recipeSharedList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRecipeSharedShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the recipeSharedList where status equals to UPDATED_STATUS
        defaultRecipeSharedShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeSharedsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        // Get all the recipeSharedList where status is not null
        defaultRecipeSharedShouldBeFound("status.specified=true");

        // Get all the recipeSharedList where status is null
        defaultRecipeSharedShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipeSharedsByRecipeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recipe recipe = recipeShared.getRecipe();
        recipeSharedRepository.saveAndFlush(recipeShared);
        Long recipeId = recipe.getId();

        // Get all the recipeSharedList where recipe equals to recipeId
        defaultRecipeSharedShouldBeFound("recipeId.equals=" + recipeId);

        // Get all the recipeSharedList where recipe equals to recipeId + 1
        defaultRecipeSharedShouldNotBeFound("recipeId.equals=" + (recipeId + 1));
    }


    @Test
    @Transactional
    public void getAllRecipeSharedsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = recipeShared.getUser();
        recipeSharedRepository.saveAndFlush(recipeShared);
        Long userId = user.getId();

        // Get all the recipeSharedList where user equals to userId
        defaultRecipeSharedShouldBeFound("userId.equals=" + userId);

        // Get all the recipeSharedList where user equals to userId + 1
        defaultRecipeSharedShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecipeSharedShouldBeFound(String filter) throws Exception {
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeShared.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecipeSharedShouldNotBeFound(String filter) throws Exception {
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeShared() throws Exception {
        // Get the recipeShared
        restRecipeSharedMockMvc.perform(get("/api/recipe-shareds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeShared() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        int databaseSizeBeforeUpdate = recipeSharedRepository.findAll().size();

        // Update the recipeShared
        RecipeShared updatedRecipeShared = recipeSharedRepository.findById(recipeShared.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeShared are not directly saved in db
        em.detach(updatedRecipeShared);
        updatedRecipeShared
            .status(UPDATED_STATUS);
        RecipeSharedDTO recipeSharedDTO = recipeSharedMapper.toDto(updatedRecipeShared);

        restRecipeSharedMockMvc.perform(put("/api/recipe-shareds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeSharedDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeShared in the database
        List<RecipeShared> recipeSharedList = recipeSharedRepository.findAll();
        assertThat(recipeSharedList).hasSize(databaseSizeBeforeUpdate);
        RecipeShared testRecipeShared = recipeSharedList.get(recipeSharedList.size() - 1);
        assertThat(testRecipeShared.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeShared() throws Exception {
        int databaseSizeBeforeUpdate = recipeSharedRepository.findAll().size();

        // Create the RecipeShared
        RecipeSharedDTO recipeSharedDTO = recipeSharedMapper.toDto(recipeShared);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeSharedMockMvc.perform(put("/api/recipe-shareds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeSharedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeShared in the database
        List<RecipeShared> recipeSharedList = recipeSharedRepository.findAll();
        assertThat(recipeSharedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeShared() throws Exception {
        // Initialize the database
        recipeSharedRepository.saveAndFlush(recipeShared);

        int databaseSizeBeforeDelete = recipeSharedRepository.findAll().size();

        // Delete the recipeShared
        restRecipeSharedMockMvc.perform(delete("/api/recipe-shareds/{id}", recipeShared.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipeShared> recipeSharedList = recipeSharedRepository.findAll();
        assertThat(recipeSharedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
