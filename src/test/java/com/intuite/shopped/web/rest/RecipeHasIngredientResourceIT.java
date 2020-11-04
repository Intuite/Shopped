package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.RecipeHasIngredient;
import com.intuite.shopped.domain.Ingredient;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.repository.RecipeHasIngredientRepository;
import com.intuite.shopped.service.RecipeHasIngredientService;
import com.intuite.shopped.service.dto.RecipeHasIngredientDTO;
import com.intuite.shopped.service.mapper.RecipeHasIngredientMapper;
import com.intuite.shopped.service.dto.RecipeHasIngredientCriteria;
import com.intuite.shopped.service.RecipeHasIngredientQueryService;

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
 * Integration tests for the {@link RecipeHasIngredientResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeHasIngredientResourceIT {

    private static final Integer DEFAULT_AMOUNT = 0;
    private static final Integer UPDATED_AMOUNT = 1;
    private static final Integer SMALLER_AMOUNT = 0 - 1;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private RecipeHasIngredientRepository recipeHasIngredientRepository;

    @Autowired
    private RecipeHasIngredientMapper recipeHasIngredientMapper;

    @Autowired
    private RecipeHasIngredientService recipeHasIngredientService;

    @Autowired
    private RecipeHasIngredientQueryService recipeHasIngredientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeHasIngredientMockMvc;

    private RecipeHasIngredient recipeHasIngredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeHasIngredient createEntity(EntityManager em) {
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient()
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS);
        // Add required entity
        Ingredient ingredient;
        if (TestUtil.findAll(em, Ingredient.class).isEmpty()) {
            ingredient = IngredientResourceIT.createEntity(em);
            em.persist(ingredient);
            em.flush();
        } else {
            ingredient = TestUtil.findAll(em, Ingredient.class).get(0);
        }
        recipeHasIngredient.setIngredient(ingredient);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeHasIngredient.setRecipe(recipe);
        return recipeHasIngredient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeHasIngredient createUpdatedEntity(EntityManager em) {
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient()
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        // Add required entity
        Ingredient ingredient;
        if (TestUtil.findAll(em, Ingredient.class).isEmpty()) {
            ingredient = IngredientResourceIT.createUpdatedEntity(em);
            em.persist(ingredient);
            em.flush();
        } else {
            ingredient = TestUtil.findAll(em, Ingredient.class).get(0);
        }
        recipeHasIngredient.setIngredient(ingredient);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createUpdatedEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeHasIngredient.setRecipe(recipe);
        return recipeHasIngredient;
    }

    @BeforeEach
    public void initTest() {
        recipeHasIngredient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeHasIngredient() throws Exception {
        int databaseSizeBeforeCreate = recipeHasIngredientRepository.findAll().size();
        // Create the RecipeHasIngredient
        RecipeHasIngredientDTO recipeHasIngredientDTO = recipeHasIngredientMapper.toDto(recipeHasIngredient);
        restRecipeHasIngredientMockMvc.perform(post("/api/recipe-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasIngredientDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeHasIngredient in the database
        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeHasIngredient testRecipeHasIngredient = recipeHasIngredientList.get(recipeHasIngredientList.size() - 1);
        assertThat(testRecipeHasIngredient.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRecipeHasIngredient.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRecipeHasIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeHasIngredientRepository.findAll().size();

        // Create the RecipeHasIngredient with an existing ID
        recipeHasIngredient.setId(1L);
        RecipeHasIngredientDTO recipeHasIngredientDTO = recipeHasIngredientMapper.toDto(recipeHasIngredient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeHasIngredientMockMvc.perform(post("/api/recipe-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeHasIngredient in the database
        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeHasIngredientRepository.findAll().size();
        // set the field null
        recipeHasIngredient.setAmount(null);

        // Create the RecipeHasIngredient, which fails.
        RecipeHasIngredientDTO recipeHasIngredientDTO = recipeHasIngredientMapper.toDto(recipeHasIngredient);


        restRecipeHasIngredientMockMvc.perform(post("/api/recipe-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredients() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeHasIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipeHasIngredient() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get the recipeHasIngredient
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients/{id}", recipeHasIngredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeHasIngredient.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getRecipeHasIngredientsByIdFiltering() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        Long id = recipeHasIngredient.getId();

        defaultRecipeHasIngredientShouldBeFound("id.equals=" + id);
        defaultRecipeHasIngredientShouldNotBeFound("id.notEquals=" + id);

        defaultRecipeHasIngredientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecipeHasIngredientShouldNotBeFound("id.greaterThan=" + id);

        defaultRecipeHasIngredientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecipeHasIngredientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount equals to DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount equals to UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount not equals to DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount not equals to UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the recipeHasIngredientList where amount equals to UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount is not null
        defaultRecipeHasIngredientShouldBeFound("amount.specified=true");

        // Get all the recipeHasIngredientList where amount is null
        defaultRecipeHasIngredientShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount is greater than or equal to UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount is less than or equal to DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount is less than or equal to SMALLER_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount is less than DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount is less than UPDATED_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where amount is greater than DEFAULT_AMOUNT
        defaultRecipeHasIngredientShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the recipeHasIngredientList where amount is greater than SMALLER_AMOUNT
        defaultRecipeHasIngredientShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where status equals to DEFAULT_STATUS
        defaultRecipeHasIngredientShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the recipeHasIngredientList where status equals to UPDATED_STATUS
        defaultRecipeHasIngredientShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where status not equals to DEFAULT_STATUS
        defaultRecipeHasIngredientShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the recipeHasIngredientList where status not equals to UPDATED_STATUS
        defaultRecipeHasIngredientShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRecipeHasIngredientShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the recipeHasIngredientList where status equals to UPDATED_STATUS
        defaultRecipeHasIngredientShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        // Get all the recipeHasIngredientList where status is not null
        defaultRecipeHasIngredientShouldBeFound("status.specified=true");

        // Get all the recipeHasIngredientList where status is null
        defaultRecipeHasIngredientShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByIngredientIsEqualToSomething() throws Exception {
        // Get already existing entity
        Ingredient ingredient = recipeHasIngredient.getIngredient();
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);
        Long ingredientId = ingredient.getId();

        // Get all the recipeHasIngredientList where ingredient equals to ingredientId
        defaultRecipeHasIngredientShouldBeFound("ingredientId.equals=" + ingredientId);

        // Get all the recipeHasIngredientList where ingredient equals to ingredientId + 1
        defaultRecipeHasIngredientShouldNotBeFound("ingredientId.equals=" + (ingredientId + 1));
    }


    @Test
    @Transactional
    public void getAllRecipeHasIngredientsByRecipeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Recipe recipe = recipeHasIngredient.getRecipe();
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);
        Long recipeId = recipe.getId();

        // Get all the recipeHasIngredientList where recipe equals to recipeId
        defaultRecipeHasIngredientShouldBeFound("recipeId.equals=" + recipeId);

        // Get all the recipeHasIngredientList where recipe equals to recipeId + 1
        defaultRecipeHasIngredientShouldNotBeFound("recipeId.equals=" + (recipeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecipeHasIngredientShouldBeFound(String filter) throws Exception {
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeHasIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecipeHasIngredientShouldNotBeFound(String filter) throws Exception {
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeHasIngredient() throws Exception {
        // Get the recipeHasIngredient
        restRecipeHasIngredientMockMvc.perform(get("/api/recipe-has-ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeHasIngredient() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        int databaseSizeBeforeUpdate = recipeHasIngredientRepository.findAll().size();

        // Update the recipeHasIngredient
        RecipeHasIngredient updatedRecipeHasIngredient = recipeHasIngredientRepository.findById(recipeHasIngredient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeHasIngredient are not directly saved in db
        em.detach(updatedRecipeHasIngredient);
        updatedRecipeHasIngredient
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS);
        RecipeHasIngredientDTO recipeHasIngredientDTO = recipeHasIngredientMapper.toDto(updatedRecipeHasIngredient);

        restRecipeHasIngredientMockMvc.perform(put("/api/recipe-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasIngredientDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeHasIngredient in the database
        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeUpdate);
        RecipeHasIngredient testRecipeHasIngredient = recipeHasIngredientList.get(recipeHasIngredientList.size() - 1);
        assertThat(testRecipeHasIngredient.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecipeHasIngredient.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeHasIngredient() throws Exception {
        int databaseSizeBeforeUpdate = recipeHasIngredientRepository.findAll().size();

        // Create the RecipeHasIngredient
        RecipeHasIngredientDTO recipeHasIngredientDTO = recipeHasIngredientMapper.toDto(recipeHasIngredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeHasIngredientMockMvc.perform(put("/api/recipe-has-ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeHasIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeHasIngredient in the database
        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeHasIngredient() throws Exception {
        // Initialize the database
        recipeHasIngredientRepository.saveAndFlush(recipeHasIngredient);

        int databaseSizeBeforeDelete = recipeHasIngredientRepository.findAll().size();

        // Delete the recipeHasIngredient
        restRecipeHasIngredientMockMvc.perform(delete("/api/recipe-has-ingredients/{id}", recipeHasIngredient.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipeHasIngredient> recipeHasIngredientList = recipeHasIngredientRepository.findAll();
        assertThat(recipeHasIngredientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
