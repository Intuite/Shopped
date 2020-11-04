package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Recipe;
import com.intuite.shopped.domain.User;
import com.intuite.shopped.domain.Post;
import com.intuite.shopped.repository.RecipeRepository;
import com.intuite.shopped.service.RecipeService;
import com.intuite.shopped.service.dto.RecipeDTO;
import com.intuite.shopped.service.mapper.RecipeMapper;
import com.intuite.shopped.service.dto.RecipeCriteria;
import com.intuite.shopped.service.RecipeQueryService;

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
 * Integration tests for the {@link RecipeResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORTION = 1;
    private static final Integer UPDATED_PORTION = 2;
    private static final Integer SMALLER_PORTION = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final Instant DEFAULT_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeQueryService recipeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createEntity(EntityManager em) {
        Recipe recipe = new Recipe()
            .name(DEFAULT_NAME)
            .portion(DEFAULT_PORTION)
            .description(DEFAULT_DESCRIPTION)
            .duration(DEFAULT_DURATION)
            .creation(DEFAULT_CREATION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        recipe.setUser(user);
        return recipe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipe createUpdatedEntity(EntityManager em) {
        Recipe recipe = new Recipe()
            .name(UPDATED_NAME)
            .portion(UPDATED_PORTION)
            .description(UPDATED_DESCRIPTION)
            .duration(UPDATED_DURATION)
            .creation(UPDATED_CREATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        recipe.setUser(user);
        return recipe;
    }

    @BeforeEach
    public void initTest() {
        recipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipe() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();
        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        restRecipeMockMvc.perform(post("/api/recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecipe.getPortion()).isEqualTo(DEFAULT_PORTION);
        assertThat(testRecipe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecipe.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testRecipe.getCreation()).isEqualTo(DEFAULT_CREATION);
        assertThat(testRecipe.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testRecipe.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testRecipe.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // Create the Recipe with an existing ID
        recipe.setId(1L);
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc.perform(post("/api/recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeRepository.findAll().size();
        // set the field null
        recipe.setName(null);

        // Create the Recipe, which fails.
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);


        restRecipeMockMvc.perform(post("/api/recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipes() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        restRecipeMockMvc.perform(get("/api/recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].portion").value(hasItem(DEFAULT_PORTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].creation").value(hasItem(DEFAULT_CREATION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.portion").value(DEFAULT_PORTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.creation").value(DEFAULT_CREATION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getRecipesByIdFiltering() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        Long id = recipe.getId();

        defaultRecipeShouldBeFound("id.equals=" + id);
        defaultRecipeShouldNotBeFound("id.notEquals=" + id);

        defaultRecipeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRecipeShouldNotBeFound("id.greaterThan=" + id);

        defaultRecipeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRecipeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRecipesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name equals to DEFAULT_NAME
        defaultRecipeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the recipeList where name equals to UPDATED_NAME
        defaultRecipeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name not equals to DEFAULT_NAME
        defaultRecipeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the recipeList where name not equals to UPDATED_NAME
        defaultRecipeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRecipeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the recipeList where name equals to UPDATED_NAME
        defaultRecipeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name is not null
        defaultRecipeShouldBeFound("name.specified=true");

        // Get all the recipeList where name is null
        defaultRecipeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRecipesByNameContainsSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name contains DEFAULT_NAME
        defaultRecipeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the recipeList where name contains UPDATED_NAME
        defaultRecipeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRecipesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where name does not contain DEFAULT_NAME
        defaultRecipeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the recipeList where name does not contain UPDATED_NAME
        defaultRecipeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRecipesByPortionIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion equals to DEFAULT_PORTION
        defaultRecipeShouldBeFound("portion.equals=" + DEFAULT_PORTION);

        // Get all the recipeList where portion equals to UPDATED_PORTION
        defaultRecipeShouldNotBeFound("portion.equals=" + UPDATED_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion not equals to DEFAULT_PORTION
        defaultRecipeShouldNotBeFound("portion.notEquals=" + DEFAULT_PORTION);

        // Get all the recipeList where portion not equals to UPDATED_PORTION
        defaultRecipeShouldBeFound("portion.notEquals=" + UPDATED_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsInShouldWork() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion in DEFAULT_PORTION or UPDATED_PORTION
        defaultRecipeShouldBeFound("portion.in=" + DEFAULT_PORTION + "," + UPDATED_PORTION);

        // Get all the recipeList where portion equals to UPDATED_PORTION
        defaultRecipeShouldNotBeFound("portion.in=" + UPDATED_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion is not null
        defaultRecipeShouldBeFound("portion.specified=true");

        // Get all the recipeList where portion is null
        defaultRecipeShouldNotBeFound("portion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion is greater than or equal to DEFAULT_PORTION
        defaultRecipeShouldBeFound("portion.greaterThanOrEqual=" + DEFAULT_PORTION);

        // Get all the recipeList where portion is greater than or equal to UPDATED_PORTION
        defaultRecipeShouldNotBeFound("portion.greaterThanOrEqual=" + UPDATED_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion is less than or equal to DEFAULT_PORTION
        defaultRecipeShouldBeFound("portion.lessThanOrEqual=" + DEFAULT_PORTION);

        // Get all the recipeList where portion is less than or equal to SMALLER_PORTION
        defaultRecipeShouldNotBeFound("portion.lessThanOrEqual=" + SMALLER_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsLessThanSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion is less than DEFAULT_PORTION
        defaultRecipeShouldNotBeFound("portion.lessThan=" + DEFAULT_PORTION);

        // Get all the recipeList where portion is less than UPDATED_PORTION
        defaultRecipeShouldBeFound("portion.lessThan=" + UPDATED_PORTION);
    }

    @Test
    @Transactional
    public void getAllRecipesByPortionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where portion is greater than DEFAULT_PORTION
        defaultRecipeShouldNotBeFound("portion.greaterThan=" + DEFAULT_PORTION);

        // Get all the recipeList where portion is greater than SMALLER_PORTION
        defaultRecipeShouldBeFound("portion.greaterThan=" + SMALLER_PORTION);
    }


    @Test
    @Transactional
    public void getAllRecipesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration equals to DEFAULT_DURATION
        defaultRecipeShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the recipeList where duration equals to UPDATED_DURATION
        defaultRecipeShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration not equals to DEFAULT_DURATION
        defaultRecipeShouldNotBeFound("duration.notEquals=" + DEFAULT_DURATION);

        // Get all the recipeList where duration not equals to UPDATED_DURATION
        defaultRecipeShouldBeFound("duration.notEquals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultRecipeShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the recipeList where duration equals to UPDATED_DURATION
        defaultRecipeShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration is not null
        defaultRecipeShouldBeFound("duration.specified=true");

        // Get all the recipeList where duration is null
        defaultRecipeShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration is greater than or equal to DEFAULT_DURATION
        defaultRecipeShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the recipeList where duration is greater than or equal to UPDATED_DURATION
        defaultRecipeShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration is less than or equal to DEFAULT_DURATION
        defaultRecipeShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the recipeList where duration is less than or equal to SMALLER_DURATION
        defaultRecipeShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration is less than DEFAULT_DURATION
        defaultRecipeShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the recipeList where duration is less than UPDATED_DURATION
        defaultRecipeShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where duration is greater than DEFAULT_DURATION
        defaultRecipeShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the recipeList where duration is greater than SMALLER_DURATION
        defaultRecipeShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }


    @Test
    @Transactional
    public void getAllRecipesByCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where creation equals to DEFAULT_CREATION
        defaultRecipeShouldBeFound("creation.equals=" + DEFAULT_CREATION);

        // Get all the recipeList where creation equals to UPDATED_CREATION
        defaultRecipeShouldNotBeFound("creation.equals=" + UPDATED_CREATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByCreationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where creation not equals to DEFAULT_CREATION
        defaultRecipeShouldNotBeFound("creation.notEquals=" + DEFAULT_CREATION);

        // Get all the recipeList where creation not equals to UPDATED_CREATION
        defaultRecipeShouldBeFound("creation.notEquals=" + UPDATED_CREATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByCreationIsInShouldWork() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where creation in DEFAULT_CREATION or UPDATED_CREATION
        defaultRecipeShouldBeFound("creation.in=" + DEFAULT_CREATION + "," + UPDATED_CREATION);

        // Get all the recipeList where creation equals to UPDATED_CREATION
        defaultRecipeShouldNotBeFound("creation.in=" + UPDATED_CREATION);
    }

    @Test
    @Transactional
    public void getAllRecipesByCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where creation is not null
        defaultRecipeShouldBeFound("creation.specified=true");

        // Get all the recipeList where creation is null
        defaultRecipeShouldNotBeFound("creation.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where status equals to DEFAULT_STATUS
        defaultRecipeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the recipeList where status equals to UPDATED_STATUS
        defaultRecipeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where status not equals to DEFAULT_STATUS
        defaultRecipeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the recipeList where status not equals to UPDATED_STATUS
        defaultRecipeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRecipeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the recipeList where status equals to UPDATED_STATUS
        defaultRecipeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRecipesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList where status is not null
        defaultRecipeShouldBeFound("status.specified=true");

        // Get all the recipeList where status is null
        defaultRecipeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRecipesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = recipe.getUser();
        recipeRepository.saveAndFlush(recipe);
        Long userId = user.getId();

        // Get all the recipeList where user equals to userId
        defaultRecipeShouldBeFound("userId.equals=" + userId);

        // Get all the recipeList where user equals to userId + 1
        defaultRecipeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllRecipesByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        recipe.setPost(post);
        post.setRecipe(recipe);
        recipeRepository.saveAndFlush(recipe);
        Long postId = post.getId();

        // Get all the recipeList where post equals to postId
        defaultRecipeShouldBeFound("postId.equals=" + postId);

        // Get all the recipeList where post equals to postId + 1
        defaultRecipeShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecipeShouldBeFound(String filter) throws Exception {
        restRecipeMockMvc.perform(get("/api/recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].portion").value(hasItem(DEFAULT_PORTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].creation").value(hasItem(DEFAULT_CREATION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restRecipeMockMvc.perform(get("/api/recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRecipeShouldNotBeFound(String filter) throws Exception {
        restRecipeMockMvc.perform(get("/api/recipes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRecipeMockMvc.perform(get("/api/recipes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).get();
        // Disconnect from session so that the updates on updatedRecipe are not directly saved in db
        em.detach(updatedRecipe);
        updatedRecipe
            .name(UPDATED_NAME)
            .portion(UPDATED_PORTION)
            .description(UPDATED_DESCRIPTION)
            .duration(UPDATED_DURATION)
            .creation(UPDATED_CREATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        RecipeDTO recipeDTO = recipeMapper.toDto(updatedRecipe);

        restRecipeMockMvc.perform(put("/api/recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecipe.getPortion()).isEqualTo(UPDATED_PORTION);
        assertThat(testRecipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecipe.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRecipe.getCreation()).isEqualTo(UPDATED_CREATION);
        assertThat(testRecipe.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testRecipe.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testRecipe.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeMockMvc.perform(put("/api/recipes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeDelete = recipeRepository.findAll().size();

        // Delete the recipe
        restRecipeMockMvc.perform(delete("/api/recipes/{id}", recipe.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
