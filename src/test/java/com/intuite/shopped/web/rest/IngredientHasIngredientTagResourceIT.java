package com.intuite.shopped.web.rest;

import com.intuite.shopped.ShoppedApp;
import com.intuite.shopped.domain.IngredientHasIngredientTag;
import com.intuite.shopped.domain.Ingredient;
import com.intuite.shopped.domain.IngredientTag;
import com.intuite.shopped.repository.IngredientHasIngredientTagRepository;
import com.intuite.shopped.service.IngredientHasIngredientTagService;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagDTO;
import com.intuite.shopped.service.mapper.IngredientHasIngredientTagMapper;
import com.intuite.shopped.service.dto.IngredientHasIngredientTagCriteria;
import com.intuite.shopped.service.IngredientHasIngredientTagQueryService;

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
 * Integration tests for the {@link IngredientHasIngredientTagResource} REST controller.
 */
@SpringBootTest(classes = ShoppedApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IngredientHasIngredientTagResourceIT {

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private IngredientHasIngredientTagRepository ingredientHasIngredientTagRepository;

    @Autowired
    private IngredientHasIngredientTagMapper ingredientHasIngredientTagMapper;

    @Autowired
    private IngredientHasIngredientTagService ingredientHasIngredientTagService;

    @Autowired
    private IngredientHasIngredientTagQueryService ingredientHasIngredientTagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientHasIngredientTagMockMvc;

    private IngredientHasIngredientTag ingredientHasIngredientTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientHasIngredientTag createEntity(EntityManager em) {
        IngredientHasIngredientTag ingredientHasIngredientTag = new IngredientHasIngredientTag()
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
        ingredientHasIngredientTag.setIngredient(ingredient);
        // Add required entity
        IngredientTag ingredientTag;
        if (TestUtil.findAll(em, IngredientTag.class).isEmpty()) {
            ingredientTag = IngredientTagResourceIT.createEntity(em);
            em.persist(ingredientTag);
            em.flush();
        } else {
            ingredientTag = TestUtil.findAll(em, IngredientTag.class).get(0);
        }
        ingredientHasIngredientTag.setIngredientTag(ingredientTag);
        return ingredientHasIngredientTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientHasIngredientTag createUpdatedEntity(EntityManager em) {
        IngredientHasIngredientTag ingredientHasIngredientTag = new IngredientHasIngredientTag()
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
        ingredientHasIngredientTag.setIngredient(ingredient);
        // Add required entity
        IngredientTag ingredientTag;
        if (TestUtil.findAll(em, IngredientTag.class).isEmpty()) {
            ingredientTag = IngredientTagResourceIT.createUpdatedEntity(em);
            em.persist(ingredientTag);
            em.flush();
        } else {
            ingredientTag = TestUtil.findAll(em, IngredientTag.class).get(0);
        }
        ingredientHasIngredientTag.setIngredientTag(ingredientTag);
        return ingredientHasIngredientTag;
    }

    @BeforeEach
    public void initTest() {
        ingredientHasIngredientTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientHasIngredientTag() throws Exception {
        int databaseSizeBeforeCreate = ingredientHasIngredientTagRepository.findAll().size();
        // Create the IngredientHasIngredientTag
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO = ingredientHasIngredientTagMapper.toDto(ingredientHasIngredientTag);
        restIngredientHasIngredientTagMockMvc.perform(post("/api/ingredient-has-ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientHasIngredientTagDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientHasIngredientTag in the database
        List<IngredientHasIngredientTag> ingredientHasIngredientTagList = ingredientHasIngredientTagRepository.findAll();
        assertThat(ingredientHasIngredientTagList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientHasIngredientTag testIngredientHasIngredientTag = ingredientHasIngredientTagList.get(ingredientHasIngredientTagList.size() - 1);
        assertThat(testIngredientHasIngredientTag.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createIngredientHasIngredientTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientHasIngredientTagRepository.findAll().size();

        // Create the IngredientHasIngredientTag with an existing ID
        ingredientHasIngredientTag.setId(1L);
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO = ingredientHasIngredientTagMapper.toDto(ingredientHasIngredientTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientHasIngredientTagMockMvc.perform(post("/api/ingredient-has-ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientHasIngredientTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientHasIngredientTag in the database
        List<IngredientHasIngredientTag> ingredientHasIngredientTagList = ingredientHasIngredientTagRepository.findAll();
        assertThat(ingredientHasIngredientTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIngredientHasIngredientTags() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get all the ingredientHasIngredientTagList
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientHasIngredientTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getIngredientHasIngredientTag() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get the ingredientHasIngredientTag
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags/{id}", ingredientHasIngredientTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientHasIngredientTag.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getIngredientHasIngredientTagsByIdFiltering() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        Long id = ingredientHasIngredientTag.getId();

        defaultIngredientHasIngredientTagShouldBeFound("id.equals=" + id);
        defaultIngredientHasIngredientTagShouldNotBeFound("id.notEquals=" + id);

        defaultIngredientHasIngredientTagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIngredientHasIngredientTagShouldNotBeFound("id.greaterThan=" + id);

        defaultIngredientHasIngredientTagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIngredientHasIngredientTagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get all the ingredientHasIngredientTagList where status equals to DEFAULT_STATUS
        defaultIngredientHasIngredientTagShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ingredientHasIngredientTagList where status equals to UPDATED_STATUS
        defaultIngredientHasIngredientTagShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get all the ingredientHasIngredientTagList where status not equals to DEFAULT_STATUS
        defaultIngredientHasIngredientTagShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ingredientHasIngredientTagList where status not equals to UPDATED_STATUS
        defaultIngredientHasIngredientTagShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get all the ingredientHasIngredientTagList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIngredientHasIngredientTagShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ingredientHasIngredientTagList where status equals to UPDATED_STATUS
        defaultIngredientHasIngredientTagShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        // Get all the ingredientHasIngredientTagList where status is not null
        defaultIngredientHasIngredientTagShouldBeFound("status.specified=true");

        // Get all the ingredientHasIngredientTagList where status is null
        defaultIngredientHasIngredientTagShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByIngredientIsEqualToSomething() throws Exception {
        // Get already existing entity
        Ingredient ingredient = ingredientHasIngredientTag.getIngredient();
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);
        Long ingredientId = ingredient.getId();

        // Get all the ingredientHasIngredientTagList where ingredient equals to ingredientId
        defaultIngredientHasIngredientTagShouldBeFound("ingredientId.equals=" + ingredientId);

        // Get all the ingredientHasIngredientTagList where ingredient equals to ingredientId + 1
        defaultIngredientHasIngredientTagShouldNotBeFound("ingredientId.equals=" + (ingredientId + 1));
    }


    @Test
    @Transactional
    public void getAllIngredientHasIngredientTagsByIngredientTagIsEqualToSomething() throws Exception {
        // Get already existing entity
        IngredientTag ingredientTag = ingredientHasIngredientTag.getIngredientTag();
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);
        Long ingredientTagId = ingredientTag.getId();

        // Get all the ingredientHasIngredientTagList where ingredientTag equals to ingredientTagId
        defaultIngredientHasIngredientTagShouldBeFound("ingredientTagId.equals=" + ingredientTagId);

        // Get all the ingredientHasIngredientTagList where ingredientTag equals to ingredientTagId + 1
        defaultIngredientHasIngredientTagShouldNotBeFound("ingredientTagId.equals=" + (ingredientTagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIngredientHasIngredientTagShouldBeFound(String filter) throws Exception {
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientHasIngredientTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIngredientHasIngredientTagShouldNotBeFound(String filter) throws Exception {
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientHasIngredientTag() throws Exception {
        // Get the ingredientHasIngredientTag
        restIngredientHasIngredientTagMockMvc.perform(get("/api/ingredient-has-ingredient-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientHasIngredientTag() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        int databaseSizeBeforeUpdate = ingredientHasIngredientTagRepository.findAll().size();

        // Update the ingredientHasIngredientTag
        IngredientHasIngredientTag updatedIngredientHasIngredientTag = ingredientHasIngredientTagRepository.findById(ingredientHasIngredientTag.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientHasIngredientTag are not directly saved in db
        em.detach(updatedIngredientHasIngredientTag);
        updatedIngredientHasIngredientTag
            .status(UPDATED_STATUS);
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO = ingredientHasIngredientTagMapper.toDto(updatedIngredientHasIngredientTag);

        restIngredientHasIngredientTagMockMvc.perform(put("/api/ingredient-has-ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientHasIngredientTagDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientHasIngredientTag in the database
        List<IngredientHasIngredientTag> ingredientHasIngredientTagList = ingredientHasIngredientTagRepository.findAll();
        assertThat(ingredientHasIngredientTagList).hasSize(databaseSizeBeforeUpdate);
        IngredientHasIngredientTag testIngredientHasIngredientTag = ingredientHasIngredientTagList.get(ingredientHasIngredientTagList.size() - 1);
        assertThat(testIngredientHasIngredientTag.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientHasIngredientTag() throws Exception {
        int databaseSizeBeforeUpdate = ingredientHasIngredientTagRepository.findAll().size();

        // Create the IngredientHasIngredientTag
        IngredientHasIngredientTagDTO ingredientHasIngredientTagDTO = ingredientHasIngredientTagMapper.toDto(ingredientHasIngredientTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientHasIngredientTagMockMvc.perform(put("/api/ingredient-has-ingredient-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientHasIngredientTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientHasIngredientTag in the database
        List<IngredientHasIngredientTag> ingredientHasIngredientTagList = ingredientHasIngredientTagRepository.findAll();
        assertThat(ingredientHasIngredientTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientHasIngredientTag() throws Exception {
        // Initialize the database
        ingredientHasIngredientTagRepository.saveAndFlush(ingredientHasIngredientTag);

        int databaseSizeBeforeDelete = ingredientHasIngredientTagRepository.findAll().size();

        // Delete the ingredientHasIngredientTag
        restIngredientHasIngredientTagMockMvc.perform(delete("/api/ingredient-has-ingredient-tags/{id}", ingredientHasIngredientTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientHasIngredientTag> ingredientHasIngredientTagList = ingredientHasIngredientTagRepository.findAll();
        assertThat(ingredientHasIngredientTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
