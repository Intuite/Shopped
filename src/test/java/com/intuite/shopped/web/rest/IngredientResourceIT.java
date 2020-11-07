package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.Ingredient;
import com.intuite.shopped.domain.Unit;
import com.intuite.shopped.repository.IngredientRepository;
import com.intuite.shopped.service.IngredientService;
import com.intuite.shopped.service.dto.IngredientDTO;
import com.intuite.shopped.service.mapper.IngredientMapper;
import com.intuite.shopped.service.dto.IngredientCriteria;
import com.intuite.shopped.service.IngredientQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intuite.shopped.domain.enumeration.Status;
/**
 * Integration tests for the {@link IngredientResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IngredientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientQueryService ingredientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientMockMvc;

    private Ingredient ingredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createEntity(EntityManager em) {
        Ingredient ingredient = new Ingredient()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        ingredient.setUnit(unit);
        return ingredient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createUpdatedEntity(EntityManager em) {
        Ingredient ingredient = new Ingredient()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        // Add required entity
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createUpdatedEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        ingredient.setUnit(unit);
        return ingredient;
    }

    @BeforeEach
    public void initTest() {
        ingredient = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredient() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();
        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);
        restIngredientMockMvc.perform(post("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate + 1);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredient.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIngredient.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testIngredient.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testIngredient.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // Create the Ingredient with an existing ID
        ingredient.setId(1L);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMockMvc.perform(post("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setName(null);

        // Create the Ingredient, which fails.
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);


        restIngredientMockMvc.perform(post("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientRepository.findAll().size();
        // set the field null
        ingredient.setDescription(null);

        // Create the Ingredient, which fails.
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);


        restIngredientMockMvc.perform(post("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredients() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList
        restIngredientMockMvc.perform(get("/api/ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getIngredientsByIdFiltering() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        Long id = ingredient.getId();

        defaultIngredientShouldBeFound("id.equals=" + id);
        defaultIngredientShouldNotBeFound("id.notEquals=" + id);

        defaultIngredientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIngredientShouldNotBeFound("id.greaterThan=" + id);

        defaultIngredientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIngredientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIngredientsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name equals to DEFAULT_NAME
        defaultIngredientShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ingredientList where name equals to UPDATED_NAME
        defaultIngredientShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name not equals to DEFAULT_NAME
        defaultIngredientShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ingredientList where name not equals to UPDATED_NAME
        defaultIngredientShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIngredientShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ingredientList where name equals to UPDATED_NAME
        defaultIngredientShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name is not null
        defaultIngredientShouldBeFound("name.specified=true");

        // Get all the ingredientList where name is null
        defaultIngredientShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllIngredientsByNameContainsSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name contains DEFAULT_NAME
        defaultIngredientShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ingredientList where name contains UPDATED_NAME
        defaultIngredientShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllIngredientsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where name does not contain DEFAULT_NAME
        defaultIngredientShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ingredientList where name does not contain UPDATED_NAME
        defaultIngredientShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllIngredientsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description equals to DEFAULT_DESCRIPTION
        defaultIngredientShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientList where description equals to UPDATED_DESCRIPTION
        defaultIngredientShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description not equals to DEFAULT_DESCRIPTION
        defaultIngredientShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientList where description not equals to UPDATED_DESCRIPTION
        defaultIngredientShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultIngredientShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ingredientList where description equals to UPDATED_DESCRIPTION
        defaultIngredientShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description is not null
        defaultIngredientShouldBeFound("description.specified=true");

        // Get all the ingredientList where description is null
        defaultIngredientShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllIngredientsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description contains DEFAULT_DESCRIPTION
        defaultIngredientShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientList where description contains UPDATED_DESCRIPTION
        defaultIngredientShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIngredientsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where description does not contain DEFAULT_DESCRIPTION
        defaultIngredientShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ingredientList where description does not contain UPDATED_DESCRIPTION
        defaultIngredientShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllIngredientsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where status equals to DEFAULT_STATUS
        defaultIngredientShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ingredientList where status equals to UPDATED_STATUS
        defaultIngredientShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where status not equals to DEFAULT_STATUS
        defaultIngredientShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ingredientList where status not equals to UPDATED_STATUS
        defaultIngredientShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIngredientShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ingredientList where status equals to UPDATED_STATUS
        defaultIngredientShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList where status is not null
        defaultIngredientShouldBeFound("status.specified=true");

        // Get all the ingredientList where status is null
        defaultIngredientShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllIngredientsByUnitIsEqualToSomething() throws Exception {
        // Get already existing entity
        Unit unit = ingredient.getUnit();
        ingredientRepository.saveAndFlush(ingredient);
        Long unitId = unit.getId();

        // Get all the ingredientList where unit equals to unitId
        defaultIngredientShouldBeFound("unitId.equals=" + unitId);

        // Get all the ingredientList where unit equals to unitId + 1
        defaultIngredientShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIngredientShouldBeFound(String filter) throws Exception {
        restIngredientMockMvc.perform(get("/api/ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restIngredientMockMvc.perform(get("/api/ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIngredientShouldNotBeFound(String filter) throws Exception {
        restIngredientMockMvc.perform(get("/api/ingredients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIngredientMockMvc.perform(get("/api/ingredients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIngredient() throws Exception {
        // Get the ingredient
        restIngredientMockMvc.perform(get("/api/ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient
        Ingredient updatedIngredient = ingredientRepository.findById(ingredient.getId()).get();
        // Disconnect from session so that the updates on updatedIngredient are not directly saved in db
        em.detach(updatedIngredient);
        updatedIngredient
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .status(UPDATED_STATUS);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(updatedIngredient);

        restIngredientMockMvc.perform(put("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredient.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIngredient.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testIngredient.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testIngredient.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientMockMvc.perform(put("/api/ingredients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeDelete = ingredientRepository.findAll().size();

        // Delete the ingredient
        restIngredientMockMvc.perform(delete("/api/ingredients/{id}", ingredient.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
